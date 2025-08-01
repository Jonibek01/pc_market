package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.entity.Comment;

import java.util.List;

@Component
public interface UserCommentService {
    String addUserComment(Long userId, CommentRequestDTO commentRequestDTO, Model model);
    String getAllComments(Long productId,Model model);
    Double calculateAverageRating(List<Comment> comments);
    public List<Comment> getCommentsByProductId(Long productId);
}
