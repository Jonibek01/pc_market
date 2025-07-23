package uz.app.pc_market.service.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.Category;
import uz.app.pc_market.repository.SellerRepository;
import uz.app.pc_market.service.seller.SellerCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerCategoryServiceImpl implements SellerCategoryService {
    final SellerRepository sellerRepository;
    public String getCategories(Model model) {
        List<Category> all = sellerRepository.findAll();
        if (all.isEmpty()){
            model.addAttribute("errorMessage", "categories not found");
            return "error";
        }
        model.addAttribute("categories", all);
        System.out.println("Fetched categories: " + all);
        return "seller/show-categories";
    }

    @Override
    public String createCategory(String name, Model model) {
        if (sellerRepository.existsByNameContainingIgnoreCase(name)) {
            model.addAttribute("errorMessage", "Category already exists!");
            return "error";
        }

        Category category = new Category();
        category.setName(name);
        sellerRepository.save(category);
        return "redirect:/seller-cabinet";
    }

    @Override
    public String editCategoryPage(Long id, Model model) {
        Category category = sellerRepository.findById(id).orElse(null);
        if (category == null) {
            model.addAttribute("errorMessage", "Category not found");
            return "error";
        }
        model.addAttribute("category", category);
        return "seller/edit-category";
    }

    @Override
    public String updateCategory(Long id, String name) {
        Category category = sellerRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(name);
            sellerRepository.save(category);
        }
        return "redirect:/show-categories";
    }

    @Override
    public String deleteCategory(Long id, Model model) {
        Optional<Category> categoryOptional = sellerRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            model.addAttribute("errorMessage", "Category not found");
            return "error";
        }

        Category category = categoryOptional.get();
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            model.addAttribute("errorMessage", "Cannot delete: category has subcategories");
            return "error";
        }

        sellerRepository.deleteById(id);
        return "redirect:/show-categories";
    }


}
