package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CharacteristicValue extends ABCEntity {
    private String value; // e.g. 8GB, Black, Dell

    @ManyToOne
    private Characteristics characteristic;
}
