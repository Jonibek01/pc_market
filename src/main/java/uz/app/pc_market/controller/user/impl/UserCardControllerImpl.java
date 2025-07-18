package uz.app.pc_market.controller.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.user.UserCardController;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.service.user.UserCardService;
@Controller
@RequiredArgsConstructor
public class UserCardControllerImpl implements UserCardController {
    private final UserCardService userCardService;

    @Override
    public String addCard(Long userId, Card card, Model model) {
        ResponseMessage response = userCardService.addUserCard(userId, card, model);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cards", response.getData());
        model.addAttribute("userId", userId);

        return "user/cards";
    }

    @Override
    public String CardPage(Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("card", new Card());
        return "user/add-card";
    }

    @Override
    public String getAllCards(Long userId, Model model) {
        ResponseMessage response = userCardService.getAllUserCards(userId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cards", response.getData());
        model.addAttribute("userId", userId);

        return "/user/cards";
    }

    @Override
    public String updateCard(Long cardId, Double balance, Model model) {
        ResponseMessage response = userCardService.updateUserCard(cardId, balance);
        if (response.getSuccess()) {
            return "redirect:/auth/user/card/read";
        }

        model.addAttribute("success", false);
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cardId", cardId);

        return "/user/update-card";
    }

    @Override
    public String deleteCard(Long cardId, Long userId, Model model) {
        ResponseMessage response = userCardService.deleteUserCard(cardId, userId);
        if (response.getSuccess()) {
            return "redirect:/auth/user/card/read";
        }

        model.addAttribute("success", false);
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cardId", cardId);
        model.addAttribute("userId", userId);

        return "/user/delete-card";
    }
}
