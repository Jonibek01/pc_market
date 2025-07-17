package uz.app.pc_market.controller.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.entity.Card;


public interface UserCardController {
    @GetMapping("/add-card")
    String showAddCardForm(Model model);

    @PostMapping("/add-card")
    String addCard(@ModelAttribute Card card, Model model, HttpSession session);

    @GetMapping("/cards")
    String getAllCards(Model model,HttpSession session);

    @GetMapping("/update-card")
    String showUpdateCardForm(@RequestParam Long cardId, Model model);

    @PostMapping("/update-card")
    String updateCard(@RequestParam Long cardId, @RequestParam Double balance, Model model, HttpSession session);

    @GetMapping("/delete-card")
    String showDeleteCardForm(@RequestParam Long cardId, Model model);

    @PostMapping("/delete-card")
    String deleteCard(@RequestParam Long cardId, Model model,HttpSession session);
}
