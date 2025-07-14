package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "comments")
public class Comment extends ABCEntity {

    private String description;
    private Double rating;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;
}