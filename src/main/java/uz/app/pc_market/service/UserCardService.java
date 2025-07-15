package uz.app.pc_market.service;

import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.ResponseMessage;
import uz.app.pc_market.entity.Card;

@Service
public interface UserCardService {
    ResponseMessage getAllUserCards(Long userId);

    ResponseMessage addUserCard(Long userId, Card card);

    ResponseMessage updateUserCard(Long cardId, Double balance);

    ResponseMessage deleteUserCard(Long cardId, Long userId);
}
