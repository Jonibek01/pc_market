package uz.app.pc_market.controller.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.seller.SellerShowProductsController;
import uz.app.pc_market.service.seller.SellerShowProductsService;
@Controller
@RequiredArgsConstructor
public class SellerShowProductsControllerImpl implements SellerShowProductsController {
    private final SellerShowProductsService sellerShowProductsService;
    @Override
    public String showProducts(Model model) {
       return sellerShowProductsService.showProducts(model);
    }
}
