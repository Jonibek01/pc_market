package uz.app.pc_market.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private Long subCategoryId;
    private Map<Long, String> characteristics; // CharacteristicId → Selected Value
}

