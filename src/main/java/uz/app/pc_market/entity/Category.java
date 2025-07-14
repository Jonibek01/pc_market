package uz.app.pc_market.entity;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Entity
@MappedSuperclass
public class Category extends ABCEntity{
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
