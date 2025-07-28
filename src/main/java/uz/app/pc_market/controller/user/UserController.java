package uz.app.pc_market.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.dto.userdto.ProductFilterDTO;
import uz.app.pc_market.entity.Basket;

@RequestMapping("/user")
public interface UserController {
    @GetMapping("/basket/baskets")
    String getBaskets(Model model, HttpSession session);

    @PostMapping("/basket/add")
    String addItemToBasket(
            Model model,
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            HttpSession session
    );

    @GetMapping("/basket/add")
    String getOrderMenuPage(
            Model model,
            @RequestParam(value = "productId", required = false) Long productId,
            HttpSession session
    );

    @PostMapping("/basket/clear")
    String clearBasket(Model model, HttpSession session);

    @PostMapping("/basket/delete")
    String deleteBasketItem(@RequestParam("basketItemId") Long basketItemId
            , RedirectAttributes redirectAttributes
            , HttpSession session
    );

    @PostMapping("/basket/buy")
    String buyBasket(RedirectAttributes redirectAttributes,HttpSession session);

    @GetMapping("/basket/buy")
    String showBuyConfirmation(Model model);

    @PostMapping("/basket/update-quantity")
    String updateBasketItemQuantity(
            @RequestParam("basketItemId") Long basketItemId,
            @RequestParam("quantity") Integer quantity,
            RedirectAttributes redirectAttributes,
            HttpSession session
    );

    @GetMapping("/basket/details/{basketId}")
    String getBasketDetails(@PathVariable Long basketId, Model model, HttpSession session);

    @PostMapping("/basket/validate")
    String validateBasket(RedirectAttributes redirectAttributes, HttpSession session);


    @GetMapping("/comment/add-comment")
    String showAddCommentForm(Model model);

    @PostMapping("/comment/add-comment")
    String addUserComment(@RequestParam Long userId, @ModelAttribute CommentRequestDTO commentRequestDTO, BindingResult result, Model model);

    @GetMapping("/comment/comments")
    String getAllComments(@RequestParam Long productId, Model model,HttpSession session);


    @GetMapping("/history/histories")
    String getUserHistory(Model model,HttpSession session);


    @GetMapping("/products")
    String getProducts(Model model);


    @GetMapping("/products/filter")
    String showFilterForm(Model model);

    @PostMapping("/products/filter")
    String filterProducts(@ModelAttribute("filterDto") ProductFilterDTO filterDto,BindingResult bindingResult, Model model);
}