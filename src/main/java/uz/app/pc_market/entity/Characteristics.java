package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.app.pc_market.entity.enums.CharacteristicType;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Characteristics extends ABCEntity{
    private String name; // e.g. "RAM"
    private CharacteristicType type;
}
