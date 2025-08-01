package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import uz.app.pc_market.entity.Basket;
import uz.app.pc_market.entity.BasketItem;

import java.util.List;

@Component
public interface UserBasketService {
    Basket getUserBasket(Long userId);
    List<BasketItem> getBasketItems(Long basketId);
    Basket addProductToBasket(Long productId, Integer quantity, Long userId);
    Basket deleteFromBasket(Long basketId, Long productId);
    String clearBasket(Long userId);
    String buyAllProducts(Long userId);
    String deleteBasketItem(Long basketItemId);
    String updateBasketItemQuantity(Long basketItemId, Integer quantity);
    String validateBasket(Long userId);

}
