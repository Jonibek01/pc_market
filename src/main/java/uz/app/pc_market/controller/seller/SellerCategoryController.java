package uz.app.pc_market.controller.seller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SellerCategoryController {
    @GetMapping("/show-categories")
    String getCategories(Model model);
    @GetMapping("/add-category")
    String addCategory(Model model);
    @PostMapping("/add-category")
    String createCategory(@RequestParam String name, Model model);
    @GetMapping("/edit-category/{id}")
    String editCategoryPage(@PathVariable Long id, Model model);
    @PostMapping("/edit-category")
    String updateCategory(@RequestParam Long id, @RequestParam String name);
    @PostMapping("/delete-category/{id}")
    String deleteCategory(@PathVariable Long id, Model model);


}
