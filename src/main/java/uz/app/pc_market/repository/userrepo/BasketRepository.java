package uz.app.pc_market.repository.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Basket;
import uz.app.pc_market.entity.enums.BasketStatus;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByIdAndUserId(Long id, Long userId);

    Optional<Basket> findByUserIdAndBasketStatus(Long userId, BasketStatus basketStatus);


}
