package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.dto.userdto.ResponseMessage;

@Component
public interface UserCommentService {
    ResponseMessage addUserComment(Long userId, CommentRequestDTO commentRequestDTO);

    ResponseMessage getAllComments(Long productId);
}
