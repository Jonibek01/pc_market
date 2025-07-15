package uz.app.pc_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.app.pc_market.entity.enums.BasketStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "histories")
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
