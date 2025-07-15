package uz.app.pc_market.controller.seller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SellerSubCategoryController {
    @GetMapping("/show-subcategories")
    String getSubCategories(Model model);

    @GetMapping("/add-subcategory")
    String addSubCategory(Model model);

    @PostMapping("/add-subcategory")
    String createSubCategory(@RequestParam String name,
                             @RequestParam Long categoryId,
                             Model model);

}
