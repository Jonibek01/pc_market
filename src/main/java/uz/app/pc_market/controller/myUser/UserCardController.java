package uz.app.pc_market.controller.myUser;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.entity.Card;

@RequestMapping("/auth/user/card")
public interface UserCardController {
    @PostMapping("/add")
    ResponseEntity<?> addCard(@RequestBody Card card);

    @GetMapping("/read")
    String getAllCards(@RequestParam Long userId, Model model);

    @PostMapping("/update")
    ResponseEntity<?> updateCard(@RequestParam Long cardId,@RequestParam Double balance);

    @PostMapping("/delete")
    ResponseEntity<?> deleteCard(@RequestParam Long cardId, @RequestParam Long userId);
}
