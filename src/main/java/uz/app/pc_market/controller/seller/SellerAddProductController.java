package uz.app.pc_market.controller.seller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.ProductCreateDto;


import java.util.List;

public interface SellerAddProductController {

    @GetMapping("/add-product")
    String showAddProductForm(Model model);

    @PostMapping("/add-product")
    String createProduct(@Valid @ModelAttribute ProductCreateDto dto);
    @GetMapping("/edit-product/{id}")
    String editProductPage(@PathVariable Long id, Model model);

    @PostMapping("/edit-product")
    String updateProduct(@ModelAttribute ProductCreateDto dto);

    @PostMapping("/delete-product/{id}")
    String deleteProduct(@PathVariable Long id);

}
