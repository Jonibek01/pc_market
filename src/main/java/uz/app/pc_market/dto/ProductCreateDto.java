package uz.app.pc_market.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private MultipartFile image;
    private Long subCategoryId;
    private Map<Long, String> characteristics; // CharacteristicId → Selected Value
}

