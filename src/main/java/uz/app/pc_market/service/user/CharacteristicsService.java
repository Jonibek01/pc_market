package uz.app.pc_market.service.user;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import uz.app.pc_market.entity.Characteristics;
import uz.app.pc_market.repository.CharacteristicsRepository;

import java.util.List;

@Service
public class CharacteristicsService {
    private final CharacteristicsRepository characteristicsRepository;

    public CharacteristicsService(CharacteristicsRepository characteristicsRepository) {
        this.characteristicsRepository = characteristicsRepository;
    }

    @Transactional
    public List<Characteristics> findAllWithValues() {
        List<Characteristics> characteristics = characteristicsRepository.findAll();
        characteristics.forEach(characteristic -> Hibernate.initialize(characteristic.getValues()));
        return characteristics;
    }
}
