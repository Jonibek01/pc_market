package uz.app.pc_market.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;

@RequestMapping("/user/comment")
public interface UserCommentController {
    @GetMapping("/comments")
    String showCommentPage(@RequestParam("productId") Long productId, Model model, HttpSession session);

    @PostMapping("/add-comment")
    String addComment(@ModelAttribute CommentRequestDTO commentRequestDTO, Model model, HttpSession session);
}
