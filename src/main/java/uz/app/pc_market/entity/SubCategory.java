package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@Entity
@MappedSuperclass
public class SubCategory extends ABCEntity{
    private String name;
}
