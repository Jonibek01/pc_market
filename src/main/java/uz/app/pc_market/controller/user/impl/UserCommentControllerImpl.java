package uz.app.pc_market.controller.user.impl;


import uz.app.pc_market.controller.user.UserCommentController;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.service.user.UserCommentService;


@Controller
@RequiredArgsConstructor
public class UserCommentControllerImpl implements UserCommentController {
    private final UserCommentService userCommentService;

    @Override
    public String showCommentPage(Long productId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userCommentService.getAllComments(productId);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("comments", response.getData());
        model.addAttribute("productId", productId);
        return "comments";
    }

    @Override
    public String addComment(CommentRequestDTO commentRequestDTO, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userCommentService.addUserComment(user.getId(), commentRequestDTO);
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("comment", response.getData());
        model.addAttribute("productId", commentRequestDTO.getProductId());
        return "redirect:/auth/user/comment?productId=" + commentRequestDTO.getProductId();
    }
}
