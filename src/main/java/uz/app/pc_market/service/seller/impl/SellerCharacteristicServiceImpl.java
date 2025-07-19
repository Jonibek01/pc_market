package uz.app.pc_market.service.seller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.CharacteristicValue;
import uz.app.pc_market.entity.Characteristics;
import uz.app.pc_market.entity.enums.CharacteristicType;
import uz.app.pc_market.repository.CharacteristicValueRepository;
import uz.app.pc_market.repository.CharacteristicsRepository;
import uz.app.pc_market.service.seller.SellerCharacteristicService;
@Service
@RequiredArgsConstructor
public class SellerCharacteristicServiceImpl implements SellerCharacteristicService {

    private final CharacteristicsRepository characteristicsRepository;
    private final CharacteristicValueRepository characteristicValueRepository;
    @Override
    public String showAddCharacteristicForm(Model model) {
        model.addAttribute("characteristics", characteristicsRepository.findAll());
        model.addAttribute("subCharacteristics", characteristicValueRepository.findAll());
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

    @Override
    public String addDefaultValue(Long characteristicId, String value) {
        Characteristics c = characteristicsRepository.findById(characteristicId)
                .orElseThrow(() -> new RuntimeException("Characteristic not found"));
        CharacteristicValue v = new CharacteristicValue();
        v.setCharacteristic(c);
        v.setValue(value);
        characteristicValueRepository.save(v);
        return "redirect:/add-characteristic";
    }


}
