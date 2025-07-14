package uz.app.pc_market.entity;

import jakarta.persistence.*;
import uz.app.pc_market.entity.enums.ProductStatus;

import java.util.List;

@Entity
@Table(name = "products")
public class Product extends ABCEntity {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;

    @ManyToOne
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCharacteristic> characteristics;

    @OneToMany(mappedBy = "product")
    private List<Comment> comments;

    @OneToMany(mappedBy = "product")
    private List<BasketItem> basketItems;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
