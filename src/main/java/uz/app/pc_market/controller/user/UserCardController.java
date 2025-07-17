package uz.app.pc_market.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.entity.Card;

@RequestMapping("/auth/user/card")
public interface UserCardController {
    @PostMapping("/add")
    String addCard(@RequestParam Long userId, @RequestBody Card card,Model model);

    @GetMapping("/read")
    String getAllCards(@RequestParam Long userId, Model model);

    @PostMapping("/update")
    String updateCard(@RequestParam Long cardId,@RequestParam Double balance,Model model);

    @PostMapping("/delete")
    String deleteCard(@RequestParam Long cardId, @RequestParam Long userId,Model model);
}
