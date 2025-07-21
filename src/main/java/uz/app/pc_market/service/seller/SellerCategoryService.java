package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Component
public interface SellerCategoryService {
    String getCategories(Model model);
    String createCategory(String name, Model model);

    String editCategoryPage(Long id, Model model);

    String updateCategory(Long id, String name);

    String deleteCategory(Long id,Model model);
}
