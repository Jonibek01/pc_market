package uz.app.pc_market.entity;

import jakarta.persistence.*;
import uz.app.pc_market.entity.enums.BasketStatus;

import java.time.LocalDateTime;

@Entity
@MappedSuperclass
public class History extends ABCEntity{
    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private BasketStatus status;

    private LocalDateTime orderedTime;

    private Integer quantity;
    private Double totalPrice;
}
