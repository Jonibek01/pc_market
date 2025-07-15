package uz.app.pc_market.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.ResponseMessage;
import uz.app.pc_market.service.UserBasketService;

@Service
@RequiredArgsConstructor
public class UserBasketServiceImpl implements UserBasketService {
    @Override
    public ResponseMessage getUserBasket(Long basketId) {

        return null;
    }

    @Override
    public ResponseMessage addToBasket(Long productId, Integer quantity) {
        return null;
    }

    @Override
    public ResponseMessage deleteFromBasket(Long basketId, Long productId) {
        return null;
    }

    @Override
    public ResponseMessage clearBasket(Long basketId) {
        return null;
    }

    @Override
    public ResponseMessage buyAllProducts(Long basketId, Long cardId) {
        return null;
    }
}
