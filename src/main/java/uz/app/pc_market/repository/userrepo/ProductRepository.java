package uz.app.pc_market.repository.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
