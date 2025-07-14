package uz.app.pc_market.entity;

import jakarta.persistence.*;

@Entity
@MappedSuperclass
public class BasketItem extends ABCEntity{
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Basket basket;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice;

}
