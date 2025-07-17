package uz.app.pc_market.controller.user.impl;

import org.springframework.http.ResponseEntity;
import uz.app.pc_market.controller.user.UserCommentController;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;

public class UserCommentControllerImpl implements UserCommentController {
    @Override
    public ResponseEntity<?> createComment(Long productId, CommentRequestDTO commentRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllComments(Long productId) {
        return null;
    }
}
