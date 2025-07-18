package uz.app.pc_market.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/basket")
public interface UserBasketController {
    @GetMapping("/baskets")
    String showBasketPage(Model model, HttpSession session);

    @PostMapping("/add-basket")
    String addToBasket(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity, Model model, HttpSession session);

    @PostMapping("/delete-basket")
    String deleteFromBasket(@RequestParam("basketId") Long basketId, @RequestParam("productId") Long productId, Model model, HttpSession session);

    @PostMapping("/clear-basket")
    String clearBasket(Model model, HttpSession session);

    @PostMapping("/buy")
    String buyAllProducts(@RequestParam("cardId") Long cardId, Model model, HttpSession session);
}
