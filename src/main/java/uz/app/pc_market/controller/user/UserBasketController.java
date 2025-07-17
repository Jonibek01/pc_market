package uz.app.pc_market.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth/user/basket")
public interface UserBasketController {
    @PostMapping("/add")
    ResponseEntity<?> addToBasket(@RequestParam Long productId, @RequestParam Integer quantity);

    @GetMapping("/read")
    ResponseEntity<?> showMyBasket(@RequestParam Long basketId);

    @PostMapping("/delete")
    ResponseEntity<?> deleteFromBasket(@RequestParam Long basketId,@RequestParam Long productId);

    @PostMapping("/clear")
    ResponseEntity<?> clearBasket(@RequestParam Long basketId);

    @PostMapping("/buy")
    ResponseEntity<?> buyAllProducts(@RequestParam Long basketId,@RequestParam Long cardId);
}
