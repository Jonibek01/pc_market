package uz.app.pc_market.controller.seller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import uz.app.pc_market.controller.seller.SellerCabinetController;
@Controller
public class SellerCabinetControllerImpl implements SellerCabinetController {
    public String sellerCabinet() {
        return  "seller/seller-cabinet";
    }
}
