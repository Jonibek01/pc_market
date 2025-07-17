package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.Basket;
import uz.app.pc_market.entity.BasketItem;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.entity.History;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.entity.enums.BasketStatus;
import uz.app.pc_market.repository.userrepo.BasketRepository;
import uz.app.pc_market.repository.userrepo.BasketItemRepository;
import uz.app.pc_market.repository.userrepo.ProductRepository;
import uz.app.pc_market.repository.userrepo.UserCardRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserBasketService;
import uz.app.pc_market.service.user.UserHistoryService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserBasketServiceImpl implements UserBasketService {
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserCardRepository userCardRepository;
    private final UserHistoryService userHistoryService;
    private final HttpSession session;

    @Override
    public ResponseMessage getUserBasket(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        if (!userOptional.get().getId().equals(getCurrentUserId())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You cannot view another user's basket")
                    .data(null)
                    .build();
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
        if (basketOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("No active basket found")
                    .data(null)
                    .build();
        }
        return ResponseMessage.builder()
                .success(true)
                .message("Basket retrieved successfully")
                .data(basketOptional.get())
                .build();
    }

    @Override
    public ResponseMessage addToBasket(Long productId, Integer quantity) {
        Optional<User> userOptional = userRepository.findById(getCurrentUserId());
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Product not found")
                    .data(null)
                    .build();
        }
        if (quantity == null || quantity <= 0) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Invalid quantity")
                    .data(null)
                    .build();
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(getCurrentUserId(), BasketStatus.ACTIVE);
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

        basket.setTotalPrice(basket.getItems().stream().mapToDouble(BasketItem::getTotalPrice).sum());
        basket.setTotalAmount(basket.getItems().stream().mapToInt(BasketItem::getQuantity).sum());
        basketRepository.save(basket);

        return ResponseMessage.builder()
                .success(true)
                .message("Product added to basket")
                .data(basket)
                .build();
    }

    @Override
    public ResponseMessage deleteFromBasket(Long basketId, Long productId) {
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Basket not found")
                    .data(null)
                    .build();
        }
        Basket basket = basketOptional.get();
        if (!basket.getUser().getId().equals(getCurrentUserId())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You cannot delete from this basket")
                    .data(null)
                    .build();
        }
        Optional<BasketItem> itemOptional = basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (itemOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Product not found in basket")
                    .data(null)
                    .build();
        }
        BasketItem item = itemOptional.get();
        basket.getItems().remove(item);
        basketItemRepository.delete(item);

        basket.setTotalPrice(basket.getItems().stream().mapToDouble(BasketItem::getTotalPrice).sum());
        basket.setTotalAmount(basket.getItems().stream().mapToInt(BasketItem::getQuantity).sum());
        basketRepository.save(basket);

        return ResponseMessage.builder()
                .success(true)
                .message("Product removed from basket")
                .data(basket)
                .build();
    }

    @Override
    public ResponseMessage clearBasket(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        if (!userOptional.get().getId().equals(getCurrentUserId())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You cannot clear this basket")
                    .data(null)
                    .build();
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
        if (basketOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("No active basket found")
                    .data(null)
                    .build();
        }
        Basket basket = basketOptional.get();
        basketItemRepository.deleteAll(basket.getItems());
        basket.getItems().clear();
        basket.setTotalPrice(0.0);
        basket.setTotalAmount(0);
        basketRepository.save(basket);

        return ResponseMessage.builder()
                .success(true)
                .message("Basket cleared successfully")
                .data(null)
                .build();
    }

    @Override
    public ResponseMessage buyAllProducts(Long userId, Long cardId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        if (!userOptional.get().getId().equals(getCurrentUserId())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You cannot buy from this basket")
                    .data(null)
                    .build();
        }
        Optional<Card> cardOptional = userCardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Card not found")
                    .data(null)
                    .build();
        }
        Card card = cardOptional.get();
        if (!card.getCardHolder().getId().equals(userId)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("This card does not belong to you")
                    .data(null)
                    .build();
        }
        Optional<Basket> basketOptional = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
        if (basketOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("No active basket found")
                    .data(null)
                    .build();
        }
        Basket basket = basketOptional.get();
        if (basket.getItems().isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Basket is empty")
                    .data(null)
                    .build();
        }
        double totalPrice = basket.getTotalPrice();
        if (card.getAmount() < totalPrice) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Insufficient card balance")
                    .data(null)
                    .build();
        }
        card.setAmount(card.getAmount() - totalPrice);
        userCardRepository.save(card);

        for (BasketItem item : basket.getItems()) {
            History history = new History();
            history.setUser(userOptional.get());
            history.setProduct(item.getProduct());
            history.setStatus(BasketStatus.CONFIRMED);
            history.setOrderedTime(LocalDateTime.now());
            history.setQuantity(item.getQuantity());
            history.setTotalPrice(item.getTotalPrice());
            userHistoryService.saveHistory(history);
        }

        basketItemRepository.deleteAll(basket.getItems());
        basket.getItems().clear();
        basket.setTotalPrice(0.0);
        basket.setTotalAmount(0);
        basket.setBasketStatus(BasketStatus.CONFIRMED);
        basketRepository.save(basket);

        return ResponseMessage.builder()
                .success(true)
                .message("All products purchased successfully")
                .data(null)
                .build();
    }

    private Long getCurrentUserId() {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            throw new IllegalStateException("User not logged in");
        }
        return user.getId();
    }
}