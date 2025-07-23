package uz.app.pc_market.service.user.impl;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.app.pc_market.entity.*;
import uz.app.pc_market.entity.enums.BasketStatus;
import uz.app.pc_market.repository.userrepo.BasketItemRepository;
import uz.app.pc_market.repository.userrepo.BasketRepository;
import uz.app.pc_market.repository.userrepo.UserHistoryRepository;
import uz.app.pc_market.repository.userrepo.UserProductRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserBasketService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBasketServiceImpl implements UserBasketService {
    private final UserRepository userRepository;
    private final UserProductRepository productRepository;
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final UserHistoryRepository historyRepository;
    private final HttpSession session;

    @Override
    @Transactional
    public Basket getUserBasket(Long userId) {
        if (userId == null) {
            log.warn("User ID is null in getUserBasket");
            return null;
        }
        try {
            Basket basket = basketRepository.findByUserIdAndBasketStatus(userId, BasketStatus.ACTIVE);
            log.info("Fetched basket for userId {}: {}", userId, basket);
            return basket;
        } catch (NoResultException e) {
            log.info("No active basket found for userId {}", userId);
            return null;
        }
    }

    @Transactional
    public List<BasketItem> getBasketItems(Long basketId) {
        if (basketId == null) {
            log.warn("Basket ID is null in getBasketItems");
            return Collections.emptyList();
        }
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (basket == null) {
            log.info("No basket found for basketId {}", basketId);
            return Collections.emptyList();
        }
        List<BasketItem> items = basket.getItems(); // Leverages @OneToMany with FetchType.EAGER
        log.info("Fetched {} items for basketId {}", items.size(), basketId);
        return items;
    }

    @Override
    @Transactional
    public Basket addProductToBasket(Long productId, Integer quantity, Long userId) {
        if (productId == null || quantity == null || quantity <= 0 || userId == null) {
            log.warn("Invalid input: productId={}, quantity={}, userId={}", productId, quantity, userId);
            return null;
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            log.warn("Product not found with ID: {}", productId);
            return null;
        }
        Basket basket = getUserBasket(userId);
        if (basket == null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                user.setId(userId);
                log.warn("User not found for userId {}, created minimal User object", userId);
            }
            basket = Basket.builder()
                    .user(user)
                    .basketStatus(BasketStatus.ACTIVE)
                    .createdTime(LocalDateTime.now())
                    .totalAmount(0)
                    .totalPrice(0.0)
                    .items(new ArrayList<>())
                    .build();
            basketRepository.save(basket);
            log.info("Created new basket for userId {}", userId);
        }

        if (basket.getTotalAmount() == null) {
            basket.setTotalAmount(0);
            log.warn("Basket totalAmount was null for basketId {}, set to 0", basket.getId());
        }
        if (basket.getTotalPrice() == null) {
            basket.setTotalPrice(0.0);
            log.warn("Basket totalPrice was null for basketId {}, set to 0.0", basket.getId());
        }

        BasketItem existingItem = basket.getItems().stream()
                .filter(item -> item.getProduct() != null && item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUnitPrice(product.getPrice());
            basketItemRepository.save(existingItem);
            log.info("Updated existing item for productId {} in basketId {}", productId, basket.getId());
        } else {
            BasketItem item = BasketItem.builder()
                    .basket(basket)
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(product.getPrice())
                    .build();
            basket.getItems().add(item);
            basketItemRepository.save(item);
            log.info("Added new item for productId {} to basketId {}", productId, basket.getId());
        }

        basket.setTotalAmount(basket.getTotalAmount() + quantity);
        basket.setTotalPrice(basket.getTotalPrice() + (product.getPrice() * quantity));
        basketRepository.save(basket);
        log.info("Updated basket totals: totalAmount={}, totalPrice={}", basket.getTotalAmount(), basket.getTotalPrice());

        return basket;

    }

    @Override
    @Transactional
    public Basket deleteFromBasket(Long basketId, Long productId) {
      if (basketId == null || productId == null) {
            log.warn("Invalid input: basketId={}, productId={}", basketId, productId);
            return null;
        }
        Basket basket = basketRepository.findById(basketId).orElse(null);
        if (basket == null) {
            log.info("No basket found for basketId {}", basketId);
            return null;
        }

        BasketItem item = basket.getItems().stream()
                .filter(i -> i.getProduct() != null && i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (item == null) {
            log.info("No item found for basketId {} and productId {}", basketId, productId);
            return basket;
        }

        // Ensure totalAmount and totalPrice are not null
        if (basket.getTotalAmount() == null) {
            basket.setTotalAmount(0);
            log.warn("Basket totalAmount was null for basketId {}, set to 0", basket.getId());
        }
        if (basket.getTotalPrice() == null) {
            basket.setTotalPrice(0.0);
            log.warn("Basket totalPrice was null for basketId {}, set to 0.0", basket.getId());
        }

        basket.getItems().remove(item);
        basket.setTotalAmount(Math.max(0, basket.getTotalAmount() - item.getQuantity()));
        basket.setTotalPrice(Math.max(0.0, basket.getTotalPrice() - (item.getUnitPrice() * item.getQuantity())));
        basketItemRepository.delete(item);
        basketRepository.save(basket);
        log.info("Deleted item for productId {} from basketId {}", productId, basketId);

        return basket;
    }

    @Transactional
    @Override
    public String clearBasket(Long userId) {
        if (userId == null) {
            log.warn("User ID is null in clearBasket");
            return "Invalid user ID";
        }
        Basket basket = getUserBasket(userId);
        if (basket == null) {
            log.info("No basket found for userId {}", userId);
            return "No basket found";
        }

        basketItemRepository.deleteByBasket_Id(basket.getId());
        basket.getItems().clear();
        basket.setTotalAmount(0);
        basket.setTotalPrice(0.0);
        basketRepository.save(basket);
        log.info("Cleared basket for userId {}", userId);

        return "Basket cleared";
    }

    @Override
    @Transactional
    public String buyAllProducts(Long userId) {
        if (userId == null) {
            log.warn("User ID is null in buyAllProducts");
            return "Invalid user ID";
        }
        Basket basket = getUserBasket(userId);
        if (basket == null) {
            log.info("No basket found for userId {}", userId);
            return "No basket found";
        }
        if (basket.getItems().isEmpty()) {
            log.info("Basket is empty for userId {}", userId);
            return "Basket is empty";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("User not found for userId {}", userId);
            return "User not found";
        }
        if (user.getBalance() == null || user.getBalance() < basket.getTotalPrice()) {
            log.warn("Insufficient balance for userId {}. Required: {}, Available: {}",
                    userId, basket.getTotalPrice(), user.getBalance());
            return "Insufficient balance";
        }
        // Validate product stock
        for (BasketItem item : basket.getItems()) {
            if (item.getProduct() == null || item.getProduct().getQuantity() < item.getQuantity()) {
                log.warn("Insufficient stock for product: {}",
                        item.getProduct() != null ? item.getProduct().getName() : "Unknown");
                return "Insufficient stock for product: " +
                        (item.getProduct() != null ? item.getProduct().getName() : "Unknown");
            }
        }
        // Deduct balance and update product quantities
        user.setBalance(user.getBalance() - basket.getTotalPrice());
        for (BasketItem item : basket.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }
        // Save purchase history
        UserHistory history = UserHistory.builder()
                .user(user)
                .items(new ArrayList<>(basket.getItems()))
                .totalPrice(basket.getTotalPrice())
                .purchaseTime(LocalDateTime.now())
                .build();
        historyRepository.save(history);

        basketItemRepository.deleteByBasket_Id(basket.getId());
        basket.getItems().clear();
        basket.setTotalAmount(0);
        basket.setTotalPrice(0.0);
        basketRepository.save(basket);
        log.info("Purchased all items for userId {}", userId);

        return "Purchase successful";
    }
    @Override
    @Transactional
    public String deleteBasketItem(Long basketItemId) {
        if (basketItemId == null) {
            log.warn("Basket item ID is null in deleteBasketItem");
            return "Invalid basket item ID";
        }
        BasketItem item = basketItemRepository.findById(basketItemId).orElse(null);
        if (item == null) {
            log.info("No basket item found for basketItemId {}", basketItemId);
            return "Basket item not found";
        }
        Basket basket = item.getBasket();
        if (basket != null) {
            if (basket.getTotalAmount() == null) {
                basket.setTotalAmount(0);
            }
            if (basket.getTotalPrice() == null) {
                basket.setTotalPrice(0.0);
            }
            basket.getItems().remove(item);
            basket.setTotalAmount(Math.max(0, basket.getTotalAmount() - item.getQuantity()));
            basket.setTotalPrice(Math.max(0.0, basket.getTotalPrice() - (item.getUnitPrice() * item.getQuantity())));
            basketRepository.save(basket);
        }
        basketItemRepository.delete(item);
        log.info("Deleted basket item with ID {}", basketItemId);
        return "Basket item deleted";
    }
    @Override
    @Transactional
    public String updateBasketItemQuantity(Long basketItemId, Integer quantity) {
        if (basketItemId == null || quantity == null || quantity <= 0) {
            log.warn("Invalid input: basketItemId={}, quantity={}", basketItemId, quantity);
            return "Invalid input";
        }
        BasketItem item = basketItemRepository.findById(basketItemId).orElse(null);
        if (item == null) {
            log.info("No basket item found for basketItemId {}", basketItemId);
            return "Basket item not found";
        }
        Basket basket = item.getBasket();
        if (basket == null) {
            log.warn("No basket found for basketItemId {}", basketItemId);
            return "Basket not found";
        }
        if (basket.getTotalAmount() == null) {
            basket.setTotalAmount(0);
        }
        if (basket.getTotalPrice() == null) {
            basket.setTotalPrice(0.0);
        }
        int oldQuantity = item.getQuantity();
        item.setQuantity(quantity);
        basket.setTotalAmount(basket.getTotalAmount() - oldQuantity + quantity);
        basket.setTotalPrice(basket.getTotalPrice() - (item.getUnitPrice() * oldQuantity) + (item.getUnitPrice() * quantity));
        basketItemRepository.save(item);
        basketRepository.save(basket);
        log.info("Updated quantity for basketItemId {} to {}", basketItemId, quantity);
        return "Quantity updated";
    }
    @Override
    @Transactional
    public String validateBasket(Long userId) {
        if (userId == null) {
            log.warn("User ID is null in validateBasket");
            return "Invalid user ID";
        }
        Basket basket = getUserBasket(userId);
        if (basket == null) {
            log.info("No basket found for userId {}", userId);
            return "No basket found";
        }
        if (basket.getItems().isEmpty()) {
            log.info("Basket is empty for userId {}", userId);
            return "Basket is empty";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getBalance() == null || user.getBalance() < basket.getTotalPrice()) {
            log.warn("Insufficient balance for userId {}", userId);
            return "Insufficient balance";
        }
        for (BasketItem item : basket.getItems()) {
            if (item.getProduct() == null || item.getProduct().getQuantity() < item.getQuantity()) {
                log.warn("Insufficient stock for product: {}", item.getProduct() != null ? item.getProduct().getName() : "Unknown");
                return "Insufficient stock for product: " + (item.getProduct() != null ? item.getProduct().getName() : "Unknown");
            }
        }
        log.info("Basket validated successfully for userId {}", userId);
        return "Valid";
    }


}