package uz.app.pc_market.service.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.Characteristics;
import uz.app.pc_market.entity.enums.CharacteristicType;
import uz.app.pc_market.repository.CharacteristicsRepository;
import uz.app.pc_market.service.seller.SellerCharacteristicService;
@Service
@RequiredArgsConstructor
public class SellerCharacteristicServiceImpl implements SellerCharacteristicService {

    private final CharacteristicsRepository characteristicsRepository;

    @Override
    public String showAddCharacteristicForm(Model model) {
        model.addAttribute("characteristics", characteristicsRepository.findAll());
        return "seller/add-characteristic";
    }

    @Override
    public String createCharacteristic(String name, CharacteristicType type, Model model) {
        Characteristics c = new Characteristics();
        c.setName(name);
        c.setType(type);
        characteristicsRepository.save(c);
        return "redirect:/add-characteristic"; // or wherever you want to go
    }
}
