package uz.app.pc_market.repository.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.pc_market.entity.Comment;

public interface UserCommentRepository extends JpaRepository<Comment, Long> {
}
