package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import uz.app.pc_market.dto.userdto.ProductFilterDTO;
import uz.app.pc_market.entity.Product;

import java.util.List;

@Component
public interface UserProductService {
    List<Product> filterProducts(ProductFilterDTO filterDTO);
}
