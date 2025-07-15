package uz.app.pc_market.controller.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.seller.SellerSubCategoryController;
import uz.app.pc_market.service.seller.SellerSubCategoryService;
@Controller
@RequiredArgsConstructor
public class SellerSubCategoryControllerImpl implements SellerSubCategoryController {

    private final SellerSubCategoryService sellerSubCategoryService;

    @Override
    public String getSubCategories(Model model) {
        return sellerSubCategoryService.getSubCategories(model);
    }

    @Override
    public String addSubCategory(Model model) {
        return "seller/add-subcategory";
    }

    @Override
    public String createSubCategory(String name, Long categoryId, Model model) {
        return sellerSubCategoryService.createSubCategory(name, categoryId, model);
    }
}
