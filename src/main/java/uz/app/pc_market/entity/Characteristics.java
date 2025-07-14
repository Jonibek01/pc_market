package uz.app.pc_market.entity;

import jakarta.persistence.Entity;
import uz.app.pc_market.entity.enums.CharacteristicType;
@Entity
public class Characteristics extends ABCEntity{
    private String name; // e.g. "RAM"
    private CharacteristicType type;
}
