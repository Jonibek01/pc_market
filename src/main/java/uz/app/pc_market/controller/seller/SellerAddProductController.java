package uz.app.pc_market.controller.seller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.ProductRequestDto;

public interface SellerAddProductController {
    @GetMapping("/add-product")
    String showAddProductForm(Model model);

    @PostMapping("/add-product")
    String saveProduct(@ModelAttribute ProductRequestDto dto,
                       Model model);
}
