package uz.app.pc_market.service.seller.impl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
//        product.setImageUrl(dto.getImageUrl());
        product.setStatus(ProductStatus.ACTIVE);
        product.setSubCategory(subCategoryRepository.findById(dto.getSubCategoryId()).orElseThrow());

        MultipartFile multipartFile = dto.getImage();

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            Path imagePath = Paths.get("src/main/resources/static/uploads", filename);

            try {
                Files.createDirectories(imagePath.getParent());

                try (InputStream inputStream = multipartFile.getInputStream();
                     OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                product.setImageUrl("/uploads/" + filename);

            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }


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

    @Override
    public String editProductPage(Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        Map<Long, String> selectedCharacteristics = product.getCharacteristics().stream()
                .collect(Collectors.toMap(
                        pc -> pc.getCharacteristic().getId(),
                        ProductCharacteristic::getValue,
                        (existing, replacement) -> existing // or replacement
                ));


        model.addAttribute("selectedCharacteristics", selectedCharacteristics);
        model.addAttribute("subCategories", subCategoryRepository.findAll());
        model.addAttribute("characteristics", characteristicsRepository.findAll());
        return "seller/edit-product";
    }


    @Override
    @Transactional
    public String updateProduct(ProductCreateDto dto) {
        Product product = productRepository.findById(dto.getId()).orElseThrow();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
//        product.setImageUrl(dto.getImageUrl());
        product.setSubCategory(subCategoryRepository.findById(dto.getSubCategoryId()).orElseThrow());

        MultipartFile multipartFile = dto.getImage();

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            Path imagePath = Paths.get("src/main/resources/static/uploads", filename);

            try {
                Files.createDirectories(imagePath.getParent());

                try (InputStream inputStream = multipartFile.getInputStream();
                     OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                product.setImageUrl("/uploads/" + filename);

            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }



        // First clear old characteristics if needed
        product.getCharacteristics().clear();
        List<ProductCharacteristic> newCharacteristics = new ArrayList<>();

        for (Map.Entry<Long, String> entry : dto.getCharacteristics().entrySet()) {
            Long charId = entry.getKey();
            String value = entry.getValue();

            Characteristics characteristic = characteristicsRepository.findById(charId).orElseThrow();

            ProductCharacteristic pc = new ProductCharacteristic();
            pc.setProduct(product);
            pc.setCharacteristic(characteristic);
            pc.setValue(value);

            newCharacteristics.add(pc);
        }

        product.setCharacteristics(newCharacteristics);
        productRepository.save(product); // This cascades to characteristics

        return "redirect:/show-products";
    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "redirect:/show-products";
    }


}
