package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.ProductRequestDto;

import java.util.List;
@Component
public interface SellerAddProductService {

    String showproducts(Model model);

    String saveProduct(String name, String description, Double price, Integer quantity,
                       String imageUrl, Long subCategoryId,
                       List<Long> charIds, List<String> charValues,
                       Model model);

    String getAllProducts(Model model);
}
