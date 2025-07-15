package uz.app.pc_market.repository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
