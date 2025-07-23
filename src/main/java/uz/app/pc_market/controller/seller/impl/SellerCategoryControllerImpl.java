package uz.app.pc_market.controller.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.seller.SellerCategoryController;
import uz.app.pc_market.service.seller.SellerCategoryService;

@Controller
@RequiredArgsConstructor
public class SellerCategoryControllerImpl implements SellerCategoryController {
final SellerCategoryService sellerCategoryService;
    public String getCategories(Model model) {
        return sellerCategoryService.getCategories(model);
    }

    @Override
    public String addCategory(Model model) {
        return "seller/add-category";
    }

    @Override
    public String createCategory(String name, Model model) {
        return sellerCategoryService.createCategory(name, model);
    }

    @Override
    public String editCategoryPage(Long id, Model model) {
        return sellerCategoryService.editCategoryPage(id, model);
    }

    @Override
    public String updateCategory(Long id, String name) {
        return sellerCategoryService.updateCategory(id, name);
    }

    @Override
    public String deleteCategory(Long id, Model model) {
        return sellerCategoryService.deleteCategory(id, model);
    }

}
