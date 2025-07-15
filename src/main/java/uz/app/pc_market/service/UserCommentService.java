package uz.app.pc_market.service;

import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.CommentRequestDTO;
import uz.app.pc_market.dto.ResponseMessage;

@Service
public interface UserCommentService {
    ResponseMessage addUserComment(Long userId, CommentRequestDTO commentRequestDTO);

    ResponseMessage getAllComments(Long productId);
}
