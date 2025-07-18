package uz.app.pc_market.controller.user.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import uz.app.pc_market.controller.user.UserBasketController;
@Controller
public class UserBasketControllerImpl implements UserBasketController {
    @Override
    public ResponseEntity<?> addToBasket(Long productId, Integer quantity) {
        return null;
    }

    @Override
    public ResponseEntity<?> showMyBasket(Long basketId) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteFromBasket(Long basketId, Long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> clearBasket(Long basketId) {
        return null;
    }

    @Override
    public ResponseEntity<?> buyAllProducts(Long basketId, Long cardId) {
        return null;
    }
}
