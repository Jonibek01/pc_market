package uz.app.pc_market.controller.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.seller.SellerCharacteristicController;
import uz.app.pc_market.entity.enums.CharacteristicType;
import uz.app.pc_market.service.seller.SellerCharacteristicService;

@Controller
@RequiredArgsConstructor
public class CharacteristicControllerImpl implements SellerCharacteristicController {
    private final SellerCharacteristicService sellerCharacteristicService;
    @Override
    public String showAddCharacteristicForm(Model model) {
        return sellerCharacteristicService.showAddCharacteristicForm(model);
    }
    @Override
    public String createCharacteristic(String name, CharacteristicType type, Model model) {
        return sellerCharacteristicService.createCharacteristic(name, type, model);
    }

}
