package uz.app.pc_market.controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/seller-cabinet")
public interface SellerCabinetController {
    @GetMapping
    String sellerCabinet();
}
