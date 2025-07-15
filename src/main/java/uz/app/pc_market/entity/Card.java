package uz.app.pc_market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.app.pc_market.entity.enums.PaymentStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card extends ABCEntity {

    private String number;
    private String password;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus cardStatus;

    @ManyToOne
    private User cardHolder;
}
