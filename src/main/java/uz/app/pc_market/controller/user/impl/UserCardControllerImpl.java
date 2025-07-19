package uz.app.pc_market.controller.user.impl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.pc_market.controller.user.UserCardController;
import uz.app.pc_market.dto.userdto.CardRequestDTO;
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
        model.addAttribute("card", new CardRequestDTO());
        return "user/card/add-card";
    }

    @Override
    public String addCard(@Valid @ModelAttribute CardRequestDTO cardDTO, BindingResult bindingResult, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("success", false);
            model.addAttribute("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("card", cardDTO);
            return "user/card/add-card";
        }
        Card card = new Card();
        card.setNumber(cardDTO.getNumber());
        card.setPassword(cardDTO.getPassword());
        card.setAmount(cardDTO.getAmount());
        card.setCardStatus(cardDTO.getCardStatus());
        ResponseMessage response = userCardService.addUserCard(userId, card);
        if (response.getSuccess()) {
            return "redirect:/user/card/cards";
        }
        model.addAttribute("success", false);
        model.addAttribute("message", response.getMessage());
        model.addAttribute("card", cardDTO);
        return "user/card/add-card";
    }

    @Override
    public String getAllCards(Model model, HttpSession session) {
        Long userId = getCurrentUserId(session);
        ResponseMessage response = userCardService.getAllUserCards(userId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("cards", response.getData());
        return "user/card/cards";
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
