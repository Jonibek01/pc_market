package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
@Component
public interface SellerSubCategoryService {
    String getSubCategories(Model model);
    String addSubCategory(Model model);
    String createSubCategory(String name, Long categoryId, Model model);
}
