package uz.app.pc_market.service.seller.impl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.ProductCreateDto;
import uz.app.pc_market.entity.Characteristics;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.entity.ProductCharacteristic;
import uz.app.pc_market.entity.enums.ProductStatus;
import uz.app.pc_market.repository.CharacteristicsRepository;
import uz.app.pc_market.repository.ProductCharacteristicRepository;
import uz.app.pc_market.repository.ProductRepository;
import uz.app.pc_market.repository.SubCategoryRepository;
import uz.app.pc_market.service.seller.SellerAddProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SellerAddProductServiceImpl implements SellerAddProductService {


    private final ProductCharacteristicRepository productCharacteristicRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CharacteristicsRepository characteristicsRepository;
    private final ProductRepository productRepository;
    @Override
    public String showAddProductForm(Model model) {
        model.addAttribute("subCategories", subCategoryRepository.findAll());
        model.addAttribute("characteristics", characteristicsRepository.findAll()); // eager load with values
        return "seller/add-product";
    }

    @Override
    @Transactional
    public String createProduct(ProductCreateDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setStatus(ProductStatus.ACTIVE);
        product.setSubCategory(subCategoryRepository.findById(dto.getSubCategoryId()).orElseThrow());

        List<ProductCharacteristic> productCharacteristics = new ArrayList<>();

        for (Map.Entry<Long, String> entry : dto.getCharacteristics().entrySet()) {
            Long charId = entry.getKey();
            String value = entry.getValue();

            Characteristics characteristic = characteristicsRepository.findById(charId).orElseThrow();

            ProductCharacteristic pc = new ProductCharacteristic();
            pc.setProduct(product);
            pc.setCharacteristic(characteristic);
            pc.setValue(value);

            productCharacteristics.add(pc);
        }

        product.setCharacteristics(productCharacteristics);
        productRepository.save(product);

        return "redirect:/add-product";
    }

}
