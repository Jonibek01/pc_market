package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.Card;

@Component
public interface UserCardService {
    ResponseMessage getAllUserCards(Long userId);
    ResponseMessage addUserCard(Long userId, Card card);
    ResponseMessage updateUserCard(Long cardId, Double balance);
    ResponseMessage deleteUserCard(Long cardId, Long userId);
}
