package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import uz.app.pc_market.entity.enums.PaymentStatus;

@Entity
@MappedSuperclass
public class Card extends ABCEntity {
    private String number;
    private String password;
    private Double amount;
    private PaymentStatus cardStatus;
    @ManyToOne
    private User cardHolder;
}
