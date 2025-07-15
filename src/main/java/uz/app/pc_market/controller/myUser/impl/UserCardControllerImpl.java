package uz.app.pc_market.controller.myUser.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.myUser.UserCardController;
import uz.app.pc_market.dto.ResponseMessage;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.service.UserCardService;

@Service
@RequiredArgsConstructor
public class UserCardControllerImpl implements UserCardController {
    private final UserCardService userCardService;

    @Override
    public ResponseEntity<?> addCard(Card card) {
        return null;
    }

    @Override
    public String getAllCards(Long userId, Model model) {
        ResponseMessage response = userCardService.getAllUserCards(userId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cards", response.getData());
        model.addAttribute("userId", userId);

        return "cards";
    }

    @Override
    public ResponseEntity<?> updateCard(Long cardId, Double balance) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteCard(Long cardId, Long userId) {
        return null;
    }
}
