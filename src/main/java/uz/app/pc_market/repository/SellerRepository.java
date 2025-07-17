package uz.app.pc_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Category;

import java.util.List;

public interface SellerRepository extends JpaRepository<Category, Long> {
    @Override
    List<Category> findAll();
    boolean existsByNameContainingIgnoreCase(String name);
}
