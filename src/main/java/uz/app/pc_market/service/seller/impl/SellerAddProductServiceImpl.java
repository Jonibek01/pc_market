package uz.app.pc_market.service.seller.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.ProductRequestDto;
import uz.app.pc_market.entity.Characteristics;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.entity.ProductCharacteristic;
import uz.app.pc_market.entity.SubCategory;
import uz.app.pc_market.entity.enums.ProductStatus;
import uz.app.pc_market.repository.CharacteristicsRepository;
import uz.app.pc_market.repository.ProductCharacteristicRepository;
import uz.app.pc_market.repository.ProductRepository;
import uz.app.pc_market.repository.SubCategoryRepository;
import uz.app.pc_market.service.seller.SellerAddProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SellerAddProductServiceImpl implements SellerAddProductService {
    private final ProductRepository productRepository;
    private final CharacteristicsRepository characteristicsRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductCharacteristicRepository productCharacteristicRepository;
    private static final Logger logger = LoggerFactory.getLogger(SellerAddProductServiceImpl.class);

    @Override
    public String showproducts(Model model) {
        if (!model.containsAttribute("productDto")) {
            model.addAttribute("productDto", new ProductRequestDto());
        }
        model.addAttribute("subCategories", subCategoryRepository.findAll());
        model.addAttribute("characteristics", characteristicsRepository.findAll());
        return "seller/add-product";
    }

    @Override
    @Transactional
    public String saveProduct(ProductRequestDto dto, Model model) {
        String name = dto.getName();
        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "Product name is required.");
            return "seller/add-product";
        }

        if (productRepository.existsByNameIgnoreCase(name.trim())) {
            model.addAttribute("error", "Product with this name already exists.");
            return "seller/add-product";
        }

        if (dto.getPrice() == null || dto.getPrice() < 0) {
            model.addAttribute("error", "Price must be a positive number.");
            return "seller/add-product";
        }

        if (dto.getQuantity() == null || dto.getQuantity() < 0) {
            model.addAttribute("error", "Quantity must be a positive number.");
            return "seller/add-product";
        }
        if (dto.getCharIds() == null || dto.getCharValues() == null ||
                dto.getCharIds().size() != dto.getCharValues().size()) {
            model.addAttribute("error", "Mismatch between characteristics and values.");
            return "seller/add-product";
        }

        if (dto.getCharIds().size() != dto.getCharValues().size()) {
            model.addAttribute("error", "Mismatch between characteristics and values.");
            return "seller/add-product";
        }

        SubCategory subCategory = subCategoryRepository.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Subcategory not found."));

        Product product = new Product();
        product.setName(name.trim());
        product.setDescription(dto.getDescription().trim());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setSubCategory(subCategory);
        product.setStatus(ProductStatus.ACTIVE);
        logger.info("saving p : {}", product);
        product = productRepository.save(product);
        logger.info("saved p : {}", product);
        System.out.println("✅ product saved: " + product.getId());

        for (int i = 0; i < dto.getCharIds().size(); i++) {
            Long charId = dto.getCharIds().get(i);
            String value = dto.getCharValues().get(i);

            Characteristics characteristic = characteristicsRepository.findById(charId)
                    .orElseThrow(() -> new IllegalArgumentException("Characteristic not found."));

            switch (characteristic.getType()) {
                case NUMBER -> {
                    try {
                        Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        model.addAttribute("error", "Value for " + characteristic.getName() + " must be a number.");
                        return "seller/add-product";
                    }
                }
                case BOOLEAN -> {
                    if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                        model.addAttribute("error", "Value for " + characteristic.getName() + " must be true or false.");
                        return "seller/add-product";
                    }
                }
                case TEXT, SELECT -> {
                    if (value.trim().isEmpty()) {
                        model.addAttribute("error", "Value for " + characteristic.getName() + " cannot be empty.");
                        return "seller/add-product";
                    }
                }
            }

            ProductCharacteristic pc = new ProductCharacteristic();
            pc.setProduct(product);
            pc.setCharacteristic(characteristic);
            pc.setValue(value.trim());
            productCharacteristicRepository.save(pc);
        }

        return "redirect:/seller-cabinet";
    }

}
