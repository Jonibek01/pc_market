package uz.app.pc_market.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.app.pc_market.entity.enums.CharacteristicType;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Characteristics extends ABCEntity{
    private String name; // e.g. "RAM"
    @OneToMany(mappedBy = "characteristic", cascade = CascadeType.ALL)
    private List<CharacteristicValue> values;
    private CharacteristicType type;
}
