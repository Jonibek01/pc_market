package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class SubCategory extends ABCEntity{
    private String name;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "subCategory")
    private List<Product> products;
}
