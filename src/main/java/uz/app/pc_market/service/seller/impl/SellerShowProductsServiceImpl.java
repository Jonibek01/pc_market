package uz.app.pc_market.service.seller.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.repository.CharacteristicValueRepository;
import uz.app.pc_market.repository.CharacteristicsRepository;
import uz.app.pc_market.repository.ProductCharacteristicRepository;
import uz.app.pc_market.repository.ProductRepository;
import uz.app.pc_market.service.seller.SellerShowProductsService;

import java.util.List;

@Service
public class SellerShowProductsServiceImpl implements SellerShowProductsService {
    private final ProductRepository productRepository;
    private final CharacteristicsRepository characteristicsRepository;
    private final CharacteristicValueRepository characteristicValueRepository;

    public SellerShowProductsServiceImpl(ProductRepository productRepository,  CharacteristicsRepository characteristicsRepository, CharacteristicValueRepository characteristicValueRepository) {
        this.productRepository = productRepository;
        this.characteristicsRepository = characteristicsRepository;
        this.characteristicValueRepository = characteristicValueRepository;
    }

    @Override
    public String showProducts(Model model) {
        List<Product> all = productRepository.findAll();
        if(all.isEmpty()){
            model.addAttribute("errorMessage", "products not found");
            return "error";
        }
        model.addAttribute("characteristics", characteristicsRepository.findAll());
        model.addAttribute("subCharacteristics", characteristicValueRepository.findAll());
        model.addAttribute("products", all);
        return "seller/show-products";
    }
}
