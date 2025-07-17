package uz.app.pc_market.service.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.Category;
import uz.app.pc_market.entity.SubCategory;
import uz.app.pc_market.repository.SellerRepository;
import uz.app.pc_market.repository.SubCategoryRepository;
import uz.app.pc_market.service.seller.SellerSubCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerSubCategoryServiceImpl implements SellerSubCategoryService {

     final SubCategoryRepository subCategoryRepository;
    private final SellerRepository sellerRepository;

    @Override
    public String getSubCategories(Model model) {
        List<SubCategory> list = subCategoryRepository.findAll();
        model.addAttribute("subCategories", list);
        return "seller/show-subcategories";
    }

    @Override
    public String addSubCategory(Model model) {
        List<Category> all = sellerRepository.findAll();
        if(all.isEmpty()){
            model.addAttribute("errorMessage", "Category not found!");
            return "error";
        }
        model.addAttribute("categories", all);
        return "seller/add-subcategory";
    }

    @Override
    public String createSubCategory(String name, Long categoryId, Model model) {
        Optional<Category> categoryOptional = sellerRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            model.addAttribute("errorMessage", "Category not found!");
            return "error";
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setCategory(categoryOptional.get());

        subCategoryRepository.save(subCategory);
        return "redirect:/add-subcategory";
    }
}
