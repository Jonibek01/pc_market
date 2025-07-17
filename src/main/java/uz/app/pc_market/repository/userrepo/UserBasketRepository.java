package uz.app.pc_market.repository.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Basket;

public interface UserBasketRepository extends JpaRepository<Basket, Long> {
}
