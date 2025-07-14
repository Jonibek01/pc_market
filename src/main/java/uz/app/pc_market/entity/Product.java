package uz.app.pc_market.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@MappedSuperclass
@Table(name = "products")
public class Product extends ABCEntity {
    private String name;
    @ManyToOne
    private SubCategory subCategory;
    private String description;
    private Double price;
    private Integer quantity;
    @ManyToMany
    private List<Basket> basketList;
    private String imageUrl;
}
