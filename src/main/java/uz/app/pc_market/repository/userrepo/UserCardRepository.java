package uz.app.pc_market.repository.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<Card,Long> {
   List<Card> findByCardHolderId(Long cardHolderId);
}
