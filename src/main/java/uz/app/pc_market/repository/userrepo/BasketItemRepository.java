package uz.app.pc_market.repository.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.app.pc_market.entity.BasketItem;

import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    List<BasketItem> getBasketItemsByBasket_Id(Long basketId);

//    findByBasketIdAndProductId
    void deleteByBasket_Id(Long basketId);
    BasketItem findByBasket_IdAndProductId(Long basketId, Long productId);

}
