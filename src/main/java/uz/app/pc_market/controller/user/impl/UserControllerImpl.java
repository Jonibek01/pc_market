package uz.app.pc_market.controller.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.entity.Basket;
import uz.app.pc_market.entity.BasketItem;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.repository.userrepo.UserProductRepository;
import uz.app.pc_market.service.user.UserBasketService;
import uz.app.pc_market.service.user.UserCommentService;
import uz.app.pc_market.service.user.UserHistoryService;
import uz.app.pc_market.controller.user.UserController;

import java.util.Collections;
import java.util.List;

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
    public String getBaskets(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to view your basket");
            return "sign-in";
        }

        Basket basket = userBasketService.getUserBasket(userId);
        model.addAttribute("basket", basket);

        List<BasketItem> basketItems = basket != null ? userBasketService.getBasketItems(basket.getId()) : Collections.emptyList();
        model.addAttribute("basketItems", basketItems);

        return "user/basket/baskets";
    }

    @Override
    public String addItemToBasket(
            Model model,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to add items to your basket");
            return "sign-in";
        }
        Basket basket = userBasketService.addProductToBasket(productId, quantity, userId);
        if (basket == null) {
            model.addAttribute("error", "Failed to add item to basket");
        }
        return "redirect:/user/basket/baskets";
    }

    @Override
    public String getOrderMenuPage(
            Model model,
            @RequestParam(value = "productId", required = false) Long productId,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to add items to your basket");
            return "sign-in";
        }
        if (productId == null) {
            model.addAttribute("error", "Product ID is required to add an item to the basket");
            return "user/products";
        }
        Basket userBasket = userBasketService.getUserBasket(userId);
        model.addAttribute("basket", userBasket);

        Product attributeValue = productRepository.findById(productId)
                .orElse(null);
        if (attributeValue == null) {
            model.addAttribute("error", "Product not found");
            return "user/products";
        }

        model.addAttribute("product", attributeValue);

        return "user/basket/add-item";
    }

    @Override
    public String clearBasket(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to clear your basket");
            return "sign-in";
        }
        String res = userBasketService.clearBasket(userId);
        if (!res.equals("Basket cleared")) {
            model.addAttribute("error", res);
            return "user/basket/baskets"; // Return to basket page with error
        }

        return "user/basket/baskets";
    }

    @Override
    public String deleteBasketItem(
            @RequestParam("basketItemId") Long basketItemId,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to delete items from your basket");
            return "redirect:/sign-in";
        }
        String result = userBasketService.deleteBasketItem(basketItemId);
        if (!result.equals("Basket item deleted")) {
            redirectAttributes.addFlashAttribute("error", result);
            return "redirect:/user/basket/baskets";
        }
        redirectAttributes.addFlashAttribute("message", "Item removed from basket");
        return "redirect:/user/basket/baskets";
    }

    @Override
    public String buyBasket(RedirectAttributes redirectAttributes, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to complete your purchase");
            return "redirect:/sign-in";
        }
        String result = userBasketService.buyAllProducts(userId);
        if (!result.equals("Purchase successful")) {
            redirectAttributes.addFlashAttribute("error", result);
            return "redirect:/user/basket/baskets";
        }
        redirectAttributes.addFlashAttribute("message", "Purchase completed successfully");
        return "redirect:/user/history/histories?userId=" + userId;
    }

    @Override
    public String showBuyConfirmation(Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to view purchase confirmation");
            return "sign-in";
        }
        Basket basket = userBasketService.getUserBasket(userId);
        model.addAttribute("basket", basket);
        List<BasketItem> basketItems = basket != null ? userBasketService.getBasketItems(basket.getId()) : Collections.emptyList();
        model.addAttribute("basketItems", basketItems);
        return "user/basket/buy-confirmation";
    }

    @Override
    public String updateBasketItemQuantity(
            @RequestParam("basketItemId") Long basketItemId,
            @RequestParam("quantity") Integer quantity,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to update your basket");
            return "redirect:/sign-in";
        }
        String result = userBasketService.updateBasketItemQuantity(basketItemId, quantity);
        if (!result.equals("Quantity updated")) {
            redirectAttributes.addFlashAttribute("error", result);
            return "redirect:/user/basket/baskets";
        }
        redirectAttributes.addFlashAttribute("message", "Quantity updated successfully");
        return "redirect:/user/basket/baskets";
    }

    @Override
    public String getBasketDetails(
            @PathVariable Long basketId,
            Model model,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to view basket details");
            return "sign-in";
        }
        Basket basket = userBasketService.getUserBasket(userId);
        if (basket == null || !basket.getId().equals(basketId)) {
            model.addAttribute("error", "Basket not found");
            return "user/basket/baskets";
        }
        model.addAttribute("basket", basket);
        model.addAttribute("basketItems", userBasketService.getBasketItems(basketId));
        return "user/basket/basket-details";
    }

    @Override
    public String validateBasket(RedirectAttributes redirectAttributes, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to validate your basket");
            return "redirect:/sign-in";
        }
        String result = userBasketService.validateBasket(userId);
        if (!result.equals("Valid")) {
            redirectAttributes.addFlashAttribute("error", result);
            return "redirect:/user/basket/baskets";
        }
        return "redirect:/user/basket/buy";
    }

    // Comment-related endpoints
    @Override
    public String showAddCommentForm(Model model) {
        model.addAttribute("commentDto", new CommentRequestDTO());
        model.addAttribute("products", productRepository.findAll());
        return "user/comment/add-comment";
    }

    @Override
    public String addUserComment(@RequestParam Long userId, @ModelAttribute("commentDto") CommentRequestDTO commentRequestDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("products", productRepository.findAll());
            return "user/comment/add-comment";
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

    @GetMapping("/products")
    public String getProducts(Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please log in to view products");
            return "sign-in";
        }
        model.addAttribute("products", productRepository.findAll());
        return "user/products";
    }
}