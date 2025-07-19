package uz.app.pc_market.controller.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;

@RequestMapping("/user")
public interface UserController {
    // Basket-related endpoints
    @GetMapping("/basket/baskets")
    String getUserBasket(@RequestParam Long userId, Model model);

    @PostMapping("/basket/add")
    String addToBasket(@RequestParam Long productId, @RequestParam Integer quantity, Model model);

    @PostMapping("/basket/delete")
    String deleteFromBasket(@RequestParam Long basketId, @RequestParam Long productId, Model model);

    @PostMapping("/basket/clear")
    String clearBasket(@RequestParam Long userId, Model model);

    @PostMapping("/basket/buy")
    String buyAllProducts(@RequestParam Long userId, Model model);

    // Comment-related endpoints
    @PostMapping("/comment/add-comment")
    String addUserComment(@RequestParam Long userId, @ModelAttribute CommentRequestDTO commentRequestDTO, BindingResult result, Model model);

    @GetMapping("/comment/comments")
    String getAllComments(@RequestParam Long productId, Model model);

    // History-related endpoints
    @GetMapping("/history/histories")
    String getUserHistory(@RequestParam Long userId, Model model);
}