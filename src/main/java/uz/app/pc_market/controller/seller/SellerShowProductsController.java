package uz.app.pc_market.controller.seller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface SellerShowProductsController {
    @GetMapping("/show-products")
    String showProducts(Model model);
}
