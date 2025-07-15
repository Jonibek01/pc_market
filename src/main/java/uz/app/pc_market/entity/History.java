package uz.app.pc_market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.app.pc_market.entity.enums.BasketStatus;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
