package uz.app.pc_market.controller.myUser;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.CommentRequestDTO;

@RequestMapping("/auth/user/comment")
public interface UserCommentController {
    @PostMapping("/create")
    ResponseEntity<?> createComment(@RequestParam Long productId, @RequestBody CommentRequestDTO commentRequestDTO);

    @GetMapping("/read")
    ResponseEntity<?> getAllComments(@RequestParam Long productId);
}
