package uz.app.pc_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.CharacteristicValue;

public interface CharacteristicValueRepository extends JpaRepository<CharacteristicValue, Long> {
}
