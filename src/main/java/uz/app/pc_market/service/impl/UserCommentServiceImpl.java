package uz.app.pc_market.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.CommentRequestDTO;
import uz.app.pc_market.dto.ResponseMessage;
import uz.app.pc_market.service.UserCommentService;

@Service
@RequiredArgsConstructor
public class UserCommentServiceImpl implements UserCommentService {
    @Override
    public ResponseMessage addUserComment(Long userId, CommentRequestDTO commentRequestDTO) {
        return null;
    }

    @Override
    public ResponseMessage getAllComments(Long productId) {
        return null;
    }
}
