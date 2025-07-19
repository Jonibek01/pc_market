package uz.app.pc_market.controller.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.repository.userrepo.UserProductRepository;
import uz.app.pc_market.service.user.UserBasketService;
import uz.app.pc_market.service.user.UserCommentService;
import uz.app.pc_market.service.user.UserHistoryService;
import uz.app.pc_market.controller.user.UserController;

@Controller
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserBasketService userBasketService;
    private final UserCommentService userCommentService;
    private final UserHistoryService userHistoryService;
    private final UserProductRepository productRepository;
    private final HttpSession session;

    // Basket-related endpoints
    @Override
    public String getUserBasket(@RequestParam Long userId, Model model) {
        return userBasketService.getUserBasket(userId, model);
    }

    @Override
    public String addToBasket(@RequestParam Long productId, @RequestParam Integer quantity, Model model) {
        return userBasketService.addToBasket(productId, quantity, model);
    }

    @Override
    public String deleteFromBasket(@RequestParam Long basketId, @RequestParam Long productId, Model model) {
        return userBasketService.deleteFromBasket(basketId, productId, model);
    }

    @Override
    public String clearBasket(@RequestParam Long userId, Model model) {
        return userBasketService.clearBasket(userId, model);
    }

    @Override
    public String buyAllProducts(@RequestParam Long userId, Model model) {
        return userBasketService.buyAllProducts(userId, null, model);
    }

    // Comment-related endpoints
    @Override
    public String addUserComment(@RequestParam Long userId, @ModelAttribute("commentDto") CommentRequestDTO commentRequestDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("products", productRepository.findAll());
            return "user/comments/add-comment";
        }
        return userCommentService.addUserComment(userId, commentRequestDTO, model);
    }

    @Override
    public String getAllComments(@RequestParam Long productId, Model model) {
        return userCommentService.getAllComments(productId, model);
    }

    // History-related endpoints
    @Override
    public String getUserHistory(@RequestParam Long userId, Model model) {
        return userHistoryService.getUserHistory(userId, model);
    }
}