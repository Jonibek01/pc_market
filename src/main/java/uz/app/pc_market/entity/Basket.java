package uz.app.pc_market.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.app.pc_market.entity.enums.BasketStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "baskets")
@Builder
public class Basket extends ABCEntity {

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private BasketStatus basketStatus = BasketStatus.ACTIVE;

    private LocalDateTime createdTime = LocalDateTime.now();

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<BasketItem> items = new ArrayList<>();

    private Double totalPrice = 0.0;
    private Integer totalAmount = 0;
}