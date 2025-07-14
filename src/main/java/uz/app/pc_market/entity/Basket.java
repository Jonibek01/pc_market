package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import uz.app.pc_market.entity.enums.BasketStatus;

import java.time.LocalDateTime;


@Entity
@MappedSuperclass
public class Basket extends ABCEntity{
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;
    private BasketStatus basketStatus;
    private LocalDateTime createdTime = LocalDateTime.now();
    private Integer totalAmount;
    private Double totalPrice;
}
