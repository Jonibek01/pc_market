package uz.app.pc_market.service.seller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.enums.CharacteristicType;

@Component
public interface SellerCharacteristicService {

    String showAddCharacteristicForm(Model model) ;
    String createCharacteristic(String name, CharacteristicType type, Model model);

}
