package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductCharacteristic extends ABCEntity{
    @ManyToOne
    private Product product;

    @ManyToOne
    private Characteristics characteristic;

    private String value;
}
