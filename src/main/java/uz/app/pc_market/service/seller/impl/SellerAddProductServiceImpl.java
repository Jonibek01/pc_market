package uz.app.pc_market.service.seller.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
        System.out.println("🔍 Loading Add Product page...");

        model.addAttribute("subCategories", subCategoryRepository.findAll());
        model.addAttribute("characteristics", characteristicsRepository.findAll());

        return "seller/add-product";
    }



    @Override
    @Transactional
    public String saveProduct(String name, String description, Double price, Integer quantity,
                              String imageUrl, Long subCategoryId,
                              List<Long> charIds, List<String> charValues,
                              Model model) {
        System.out.println("⚠ Entered saveProduct() method");

        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "Product name is required.");
            return "seller/add-product";
        }

        if (productRepository.existsByNameIgnoreCase(name.trim())) {
            model.addAttribute("error", "Product with this name already exists.");
            return "seller/add-product";
        }

        if (price == null || price < 0) {
            model.addAttribute("error", "Price must be a positive number.");
            return "seller/add-product";
        }

        if (quantity == null || quantity < 0) {
            model.addAttribute("error", "Quantity must be a positive number.");
            return "seller/add-product";
        }

        if (charIds == null || charValues == null || charIds.size() != charValues.size()) {
            model.addAttribute("error", "Mismatch between characteristics and values.");
            return "seller/add-product";
        }

        System.out.println("charIds: " + charIds);
        System.out.println("charValues: " + charValues);

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Subcategory not found."));

        Product product = new Product();
        product.setName(name.trim());
        product.setDescription(description.trim());
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setImageUrl(imageUrl);
        product.setSubCategory(subCategory);
        product.setStatus(ProductStatus.ACTIVE);
        product = productRepository.save(product);
        System.out.println("✅ Product saved: " + product.getId());

        for (int i = 0; i < charIds.size(); i++) {
            Long charId = charIds.get(i);
            String value = charValues.get(i);

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
    @Override
    public List<Product> getAllProducts(Model model) {
        logger.info("🔍 Loading all products for seller...");
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        if (products.isEmpty()) {
            model.addAttribute("message", "No products found in the catalog.");
            model.addAttribute("success", false);
        } else {
            model.addAttribute("success", true);
        }
        return products;
    }


}
