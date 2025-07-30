package uz.app.pc_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.app.pc_market.entity.Characteristics;

import java.util.List;

public interface CharacteristicsRepository extends JpaRepository<Characteristics, Long> {
    @Query("SELECT c FROM Characteristics c LEFT JOIN FETCH c.values")
    List<Characteristics> findAllWithValues();
}
