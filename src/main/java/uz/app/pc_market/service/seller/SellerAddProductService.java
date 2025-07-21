package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.ProductCreateDto;

import java.util.List;
@Component
public interface SellerAddProductService {

    String showAddProductForm(Model model);

    String createProduct(ProductCreateDto dto);

    String editProductPage(Long id, Model model);
    String updateProduct(ProductCreateDto dto);
    String deleteProduct(Long id);

}
