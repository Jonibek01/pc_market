package uz.app.pc_market.controller.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.entity.Card;


public interface UserCardController {
    @PostMapping("/add-card")
    String addCard(@RequestParam Long userId, @RequestBody Card card,Model model);

    @GetMapping("/add-card")
    String CardPage(@RequestParam Long userId, Model model);


    @GetMapping("/cards")
    String getAllCards(@RequestParam Long userId, Model model);

    @PutMapping("/update-card")
    String updateCard(@RequestParam Long cardId,@RequestParam Double balance,Model model);


    @DeleteMapping("/delete-card")
    String deleteCard(@RequestParam Long cardId, @RequestParam Long userId,Model model);
}
