package uz.app.pc_market.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private Long subCategoryId;
    // ✅ Initialize to empty list to avoid NullPointerException
    private List<Long> charIds = new ArrayList<>();
    private List<String> charValues = new ArrayList<>();
}
