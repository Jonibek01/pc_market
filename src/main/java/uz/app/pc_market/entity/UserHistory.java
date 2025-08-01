package uz.app.pc_market.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_history")
public class UserHistory extends ABCEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private List<BasketItem> items;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "purchase_time", nullable = false)
    private LocalDateTime purchaseTime;
}
