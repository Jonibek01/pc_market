package uz.app.pc_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Category;
import uz.app.pc_market.entity.SubCategory;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByCategory_Id(Long categoryId);
}
