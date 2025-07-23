package uz.app.pc_market.controller.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.seller.SellerAddProductController;
import uz.app.pc_market.dto.ProductCreateDto;
import uz.app.pc_market.service.seller.SellerAddProductService;
@Controller
@RequiredArgsConstructor
public class SellerAddProductControllerImpl implements SellerAddProductController {
    private final SellerAddProductService sellerAddProductService;

    @Override
    public String showAddProductForm(Model model) {
        return sellerAddProductService.showAddProductForm(model);
    }

    @Override
    public String createProduct(ProductCreateDto dto) {
        return sellerAddProductService.createProduct(dto);
    }

    @Override
    public String editProductPage(Long id, Model model) {
        return sellerAddProductService.editProductPage(id, model);
    }

    @Override
    public String updateProduct(ProductCreateDto dto) {
        return sellerAddProductService.updateProduct(dto);
    }

    @Override
    public String deleteProduct(Long id) {
        return sellerAddProductService.deleteProduct(id);
    }


}
