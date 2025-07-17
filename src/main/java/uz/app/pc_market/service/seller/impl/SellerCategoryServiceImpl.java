package uz.app.pc_market.service.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.Category;
import uz.app.pc_market.repository.SellerRepository;
import uz.app.pc_market.service.seller.SellerCategoryService;

import java.util.List;
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

}
