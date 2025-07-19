package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.Basket;
import uz.app.pc_market.entity.BasketItem;
import uz.app.pc_market.entity.History;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.entity.enums.BasketStatus;
import uz.app.pc_market.repository.userrepo.BasketRepository;
import uz.app.pc_market.repository.userrepo.BasketItemRepository;
import uz.app.pc_market.repository.userrepo.UserProductRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserBasketService;
import uz.app.pc_market.service.user.UserHistoryService;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBasketServiceImpl implements UserBasketService {
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;
    private final UserProductRepository productRepository;
    private final UserHistoryService userHistoryService;
    private final HttpSession session;

    @Override
    public String getUserBasket(Long userId, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            log.error("User not logged in");
            model.addAttribute("error", "Please log in to view your basket");
            return "sign-in";
        }
        if (!userId.equals(currentUserId)) {
            log.error("User {} cannot access basket of user {}", currentUserId, userId);
            model.addAttribute("error", "You cannot access this basket");
            return "error";
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
        if (basketOptional.isEmpty()) {
            log.info("No active basket found for user with ID: {}", userId);
            model.addAttribute("baskets", null);
        } else {
            model.addAttribute("baskets", basketOptional.get().getItems());
            log.info("Basket retrieved successfully for user with ID: {}", userId);
        }
        return "user/basket/baskets";
    }

    @Override
    public String addToBasket(Long productId, Integer quantity, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            log.error("User not logged in");
            model.addAttribute("error", "Please log in to add items to basket");
            return "sign-in";
        }
        Optional<User> userOptional = userRepository.findById(currentUserId);
        if (userOptional.isEmpty()) {
            log.error("User not found: {}", currentUserId);
            model.addAttribute("error", "User not found");
            return "error";
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            log.error("Product not found: {}", productId);
            model.addAttribute("error", "Product not found");
            return "error";
        }
        if (quantity == null || quantity <= 0) {
            log.error("Invalid quantity: {}", quantity);
            model.addAttribute("error", "Invalid quantity");
            return "error";
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(currentUserId, BasketStatus.ACTIVE);
        Basket basket;
        if (basketOptional.isEmpty()) {
            basket = new Basket();
            basket.setUser(userOptional.get());
            basket.setBasketStatus(BasketStatus.ACTIVE);
            basket.setCreatedTime(LocalDateTime.now());
            basket.setTotalPrice(0.0);
            basket.setTotalAmount(0);
            basket = basketRepository.save(basket);
        } else {
            basket = basketOptional.get();
        }

        Optional<BasketItem> existingItem = basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (existingItem.isPresent()) {
            BasketItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setUnitPrice(productOptional.get().getPrice() * item.getQuantity());
            basketItemRepository.save(item);
        } else {
            BasketItem item = new BasketItem();
            item.setBasket(basket);
            item.setProduct(productOptional.get());
            item.setQuantity(quantity);
            item.setUnitPrice(productOptional.get().getPrice() * quantity);
            basket.getItems().add(item);
            basketItemRepository.save(item);
        }

        basket.setTotalPrice(basket.getItems().stream().mapToDouble(BasketItem::getUnitPrice).sum());
        basket.setTotalAmount(basket.getItems().stream().mapToInt(BasketItem::getQuantity).sum());
        basketRepository.save(basket);

        log.info("Product with ID: {} added to basket for user with ID: {}", productId, currentUserId);
        return "redirect:/user/basket/baskets";
    }

    @Override
    public String deleteFromBasket(Long basketId, Long productId, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            log.error("User not logged in");
            model.addAttribute("error", "Please log in to modify basket");
            return "sign-in";
        }
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isEmpty()) {
            log.error("Basket not found: {}", basketId);
            model.addAttribute("error", "Basket not found");
            return "error";
        }
        Basket basket = basketOptional.get();
        if (!basket.getUser().getId().equals(currentUserId)) {
            log.error("User {} cannot delete from basket {}", currentUserId, basketId);
            model.addAttribute("error", "You cannot delete from this basket");
            return "error";
        }
        Optional<BasketItem> itemOptional = basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (itemOptional.isEmpty()) {
            log.info("No item found for product with ID: {}", productId);
            model.addAttribute("error", "Product not found in basket");
            return "error";
        }
        BasketItem item = itemOptional.get();
        basket.getItems().remove(item);
        basketItemRepository.delete(item);

        basket.setTotalPrice(basket.getItems().stream().mapToDouble(BasketItem::getUnitPrice).sum());
        basket.setTotalAmount(basket.getItems().stream().mapToInt(BasketItem::getQuantity).sum());
        basketRepository.save(basket);

        log.info("Product with ID: {} deleted from basket with ID: {}", productId, basketId);
        return "redirect:/user/basket/baskets";
    }

    @Override
    public String clearBasket(Long userId, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            log.error("User not logged in");
            model.addAttribute("error", "Please log in to clear basket");
            return "sign-in";
        }
        if (!userId.equals(currentUserId)) {
            log.error("User {} cannot clear basket of user {}", currentUserId, userId);
            model.addAttribute("error", "You cannot clear this basket");
            return "error";
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
        if (basketOptional.isEmpty()) {
            log.info("No active basket found for user with ID: {}", userId);
            model.addAttribute("error", "No active basket found");
            return "error";
        }
        Basket basket = basketOptional.get();
        basketItemRepository.deleteAll(basket.getItems());
        basket.getItems().clear();
        basket.setTotalPrice(0.0);
        basket.setTotalAmount(0);
        basketRepository.save(basket);

        log.info("Basket cleared successfully for user with ID: {}", userId);
        return "redirect:/user/basket/baskets";
    }
    @Override
    @Transactional
    public String buyAllProducts(Long userId, Double balance, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            model.addAttribute("error", "Please log in to buy products");
            return "sign-in";
        }
        if (!userId.equals(currentUserId)) {
            model.addAttribute("error", "You cannot buy products from this basket");
            return "error";
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error";
        }
        User currentUser = userOptional.get();
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
        if (basketOptional.isEmpty()) {
            model.addAttribute("error", "No active basket found");
            return "error";
        }
        Basket basket = basketOptional.get();
        if (basket.getItems().isEmpty()) {
            model.addAttribute("error", "Basket is empty");
            return "error";
        }
        double totalPrice = basket.getTotalPrice();
        if (currentUser.getBalance() < totalPrice) {
            model.addAttribute("error", "Insufficient balance");
            return "error";
        }
        currentUser.setBalance(currentUser.getBalance() - totalPrice);
        userRepository.save(currentUser);

        for (BasketItem item : basket.getItems()) {
            History history = new History();
            history.setUser(currentUser);
            history.setProduct(item.getProduct());
            history.setStatus(BasketStatus.CONFIRMED);
            history.setOrderedTime(LocalDateTime.now());
            history.setQuantity(item.getQuantity());
            history.setTotalPrice(item.getUnitPrice());
            userHistoryService.saveHistory(history, model);
        }

        basketItemRepository.deleteAll(basket.getItems());
        basket.getItems().clear();
        basket.setTotalPrice(0.0);
        basket.setTotalAmount(0);
        basket.setBasketStatus(BasketStatus.CONFIRMED);
        basketRepository.save(basket);

        model.addAttribute("success", "All products bought successfully");
        return "redirect:/user/basket/baskets";
    }

    private Long getCurrentUserId() {
        return (Long) session.getAttribute("userId");
    }
}