package uz.app.pc_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.User;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
