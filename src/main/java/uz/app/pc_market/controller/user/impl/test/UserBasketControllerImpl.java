//package uz.app.pc_market.controller.user.impl;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import uz.app.pc_market.controller.user.test.UserBasketController;
//import uz.app.pc_market.dto.userdto.ResponseMessage;
//import uz.app.pc_market.service.seller.SellerAddProductService;
//import uz.app.pc_market.service.user.UserBasketService;
//
//import java.util.Collections;
//
//@Controller
//@RequiredArgsConstructor
//public class UserBasketControllerImpl implements UserBasketController {
//    private final UserBasketService userBasketService;
//    private final UserCardService userCardService;
//    @Qualifier("sellerAddProductService")
//    private final SellerAddProductService productService;
//
//    @Override
//    public String viewBasket(Model model, HttpSession session) {
//        Long userId = getUserId(model, session);
//        if (userId == null) return "sign-in";
//        ResponseMessage response = userBasketService.getUserBasket(userId);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("basket", response.getData() != null ? response.getData() : null);
//        model.addAttribute("cards", userCardService.getAllUserCards(userId).getData() != null ? userCardService.getAllUserCards(userId).getData() : Collections.emptyList());
//        model.addAttribute("products", productService.getAllProducts(model));
//        return "basket";
//    }
//
//
//    @Override
//    public String addToBasket(Long productId, Integer quantity, Model model, HttpSession session) {
//        Long userId = getUserId(model, session);
//        if (userId == null) return "sign-in";
//        ResponseMessage response = userBasketService.addToBasket(productId, quantity);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("basket", response.getData() != null ? response.getData() : null);
//        model.addAttribute("cards", userCardService.getAllUserCards(userId).getData() != null ? userCardService.getAllUserCards(userId).getData() : Collections.emptyList());
//        model.addAttribute("products", productService.getAllProducts(model));
//        return "basket";
//    }
//
//    @Override
//    public String deleteFromBasket(Long basketId, Long productId, Model model, HttpSession session) {
//        Long userId = getUserId(model, session);
//        if (userId == null) return "sign-in";
//        ResponseMessage response = userBasketService.deleteFromBasket(basketId, productId);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("basket", response.getData() != null ? response.getData() : null);
//        model.addAttribute("cards", userCardService.getAllUserCards(userId).getData() != null ? userCardService.getAllUserCards(userId).getData() : Collections.emptyList());
//        model.addAttribute("products", productService.getAllProducts(model));
//        return "basket";
//    }
//
//    @Override
//    public String clearBasket(Model model, HttpSession session) {
//        Long userId = getUserId(model, session);
//        if (userId == null) return "sign-in";
//        ResponseMessage response = userBasketService.clearBasket(userId);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("basket", response.getData() != null ? response.getData() : null);
//        model.addAttribute("cards", userCardService.getAllUserCards(userId).getData() != null ? userCardService.getAllUserCards(userId).getData() : Collections.emptyList());
//        model.addAttribute("products", productService.getAllProducts(model));
//        return "basket";
//    }
//
//    @Override
//    public String buyAllProducts(Long cardId, Model model, HttpSession session) {
//        Long userId = getUserId(model, session);
//        if (userId == null) return "sign-in";
//        ResponseMessage response = userBasketService.buyAllProducts(userId, 0.0);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("basket", response.getData() != null ? response.getData() : null);
//        model.addAttribute("cards", userCardService.getAllUserCards(userId).getData() != null ? userCardService.getAllUserCards(userId).getData() : Collections.emptyList());
//        model.addAttribute("products", productService.getAllProducts(model));
//        return "basket";
//    }
//
//
//    private static Long getUserId(Model model, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            model.addAttribute("error", "User not logged in");
//            model.addAttribute("basket", null);
//            model.addAttribute("cards", Collections.emptyList());
//            model.addAttribute("products", Collections.emptyList());
//            return null;
//        }
//        return userId;
//    }
//}