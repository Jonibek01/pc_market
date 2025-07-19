package uz.app.pc_market.controller.seller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.pc_market.entity.enums.CharacteristicType;

public interface SellerCharacteristicController {
    @GetMapping("/add-characteristic")
    String showAddCharacteristicForm(Model model);

    @PostMapping("/add-characteristic")
    String createCharacteristic(@RequestParam String name,
                                @RequestParam CharacteristicType type,
                                Model model);

    @PostMapping("/add-characteristic-value")
    String addDefaultValue(@RequestParam Long characteristicId,
                                  @RequestParam String value);
}
