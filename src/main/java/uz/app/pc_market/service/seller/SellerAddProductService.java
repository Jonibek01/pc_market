package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.ProductRequestDto;

import java.util.List;
@Component
public interface SellerAddProductService {

    String showproducts(Model model);
    String saveProduct(ProductRequestDto dto, Model model);
}
