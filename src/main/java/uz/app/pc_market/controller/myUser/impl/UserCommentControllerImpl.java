package uz.app.pc_market.controller.myUser.impl;

import org.springframework.http.ResponseEntity;
import uz.app.pc_market.controller.myUser.UserCommentController;
import uz.app.pc_market.dto.CommentRequestDTO;

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
