package uz.app.pc_market.controller.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.user.UserBasketController;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.service.seller.SellerAddProductService;
import uz.app.pc_market.service.user.UserBasketService;
import uz.app.pc_market.service.user.UserCardService;


@Controller
@RequiredArgsConstructor
public class UserBasketControllerImpl implements UserBasketController {
    private final UserBasketService userBasketService;
    private final UserCardService userCardService;
    @Qualifier("sellerAddProductService")
    private final SellerAddProductService productService;

    @Override
    public String showBasketPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userBasketService.getUserBasket(userId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("basket", response.getData());
        model.addAttribute("cards", userCardService.getAllUserCards(userId).getData());
        model.addAttribute("products", productService.getAllProducts(model));
        return "basket";
    }

    @Override
    public String addToBasket(Long productId, Integer quantity, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userBasketService.addToBasket(productId, quantity);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("basket", response.getData());
        return "basket";
    }

    @Override
    public String deleteFromBasket(Long basketId, Long productId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userBasketService.deleteFromBasket(basketId, productId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("basket", response.getData());
        return "basket";
    }

    @Override
    public String clearBasket(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userBasketService.clearBasket(user.getId());
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        return "basket";
    }

    @Override
    public String buyAllProducts(Long cardId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userBasketService.buyAllProducts(user.getId(), cardId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        return "basket";
    }
}
