package uz.app.pc_market.controller.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.pc_market.controller.user.UserCardController;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.service.user.UserCardService;

@Controller
@RequiredArgsConstructor
public class UserCardControllerImpl implements UserCardController {
    private final UserCardService userCardService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("User not logged in");
        }
        return userId;
    }

    @Override
    public String showAddCardForm(Model model) {
        model.addAttribute("card", new Card());
        return "user/card/add-card";
    }

    @Override
    public String addCard(Card card, Model model, HttpSession session) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/auth/login";
        }
        if (card.getNumber() == null || card.getNumber().isEmpty() ||
                card.getPassword() == null || card.getPassword().isEmpty() ||
                card.getAmount() == null || card.getCardStatus() == null) {
            model.addAttribute("success", false);
            model.addAttribute("message", "All fields are required");
            model.addAttribute("card", card);
            return "user/add-card";
        }
        if (card.getNumber().length() != 16) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Card number must be 16 digits");
            model.addAttribute("card", card);
            return "user/add-card";
        }
        ResponseMessage response = userCardService.addUserCard(userId, card);
        if (response.getSuccess()) {
            return "redirect:/auth/user/card/cards";
        }
        model.addAttribute("success", false);
        model.addAttribute("message", response.getMessage());
        model.addAttribute("card", card);
        return "user/add-card";
    }

    @Override
    public String getAllCards(Model model, HttpSession session) {
        Long userId = getCurrentUserId(session);
        ResponseMessage response = userCardService.getAllUserCards(userId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cards", response.getData());
        return "user/cards";
    }

    @Override
    public String showUpdateCardForm(Long cardId, Model model) {
        model.addAttribute("cardId", cardId);
        return "user/update-card";
    }

    @Override
    public String updateCard(@RequestParam Long cardId, @RequestParam Double balance, Model model, HttpSession session) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/auth/login";
        }
        ResponseMessage response = userCardService.updateUserCard(cardId, balance);
        if (response.getSuccess()) {
            return "redirect:/user/card/cards";
        }
        model.addAttribute("success", false);
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cardId", cardId);
        return "user/card/update-card";
    }

    @Override
    public String showDeleteCardForm(Long cardId, Model model) {
        model.addAttribute("cardId", cardId);
        return "user/delete-card";
    }

    public String deleteCard(@RequestParam Long cardId, Model model, HttpSession session) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/auth/login";
        }
        ResponseMessage response = userCardService.deleteUserCard(cardId, userId);
        if (response.getSuccess()) {
            return "redirect:/user/card/cards";
        }
        model.addAttribute("success", false);
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cardId", cardId);
        return "user/card/delete-card";
    }

}
