package uz.app.pc_market.controller.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.pc_market.controller.seller.SellerAddProductController;
import uz.app.pc_market.dto.ProductRequestDto;
import uz.app.pc_market.service.seller.SellerAddProductService;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class SellerAddProductControllerImpl implements SellerAddProductController {
    private final SellerAddProductService sellerAddProductService;

    @Override
    public String showAddProductForm(Model model) {
        return sellerAddProductService.showproducts(model);
    }

    public String saveProduct(ProductRequestDto dto, Model model) {
        return sellerAddProductService.saveProduct(dto, model);
    }
}
