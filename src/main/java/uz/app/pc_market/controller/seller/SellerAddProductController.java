package uz.app.pc_market.controller.seller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.ProductRequestDto;

import java.util.List;

public interface SellerAddProductController {
    @GetMapping("/add-product")
    String showAddProductForm(Model model);

    @PostMapping("/add-product")
    String saveProduct(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam(required = false) String imageUrl,
                              @RequestParam Long subCategoryId,
                              @RequestParam(name = "charIds", required = false) List<Long> charIds,
                              @RequestParam(name = "charValues", required = false) List<String> charValues,
                              Model model);
}
