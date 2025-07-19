package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public interface UserBasketService {
    String getUserBasket(Long basketId, Model model);
    String addToBasket(Long productId, Integer quantity,Model model);
    String deleteFromBasket(Long basketId, Long productId,Model model);
    String clearBasket(Long basketId,Model model);
    String buyAllProducts(Long basketId, Double balance,Model model);
}
