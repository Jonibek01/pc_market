package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Component
public interface SellerShowProductsService {

    String showProducts(Model model);
}
