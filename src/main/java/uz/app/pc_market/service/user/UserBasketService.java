package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import uz.app.pc_market.dto.userdto.ResponseMessage;

@Component
public interface UserBasketService {
    ResponseMessage getUserBasket(Long basketId);

    ResponseMessage addToBasket(Long productId, Integer quantity);

    ResponseMessage deleteFromBasket(Long basketId, Long productId);

    ResponseMessage clearBasket(Long basketId);

    ResponseMessage buyAllProducts(Long basketId, Long cardId);
}
