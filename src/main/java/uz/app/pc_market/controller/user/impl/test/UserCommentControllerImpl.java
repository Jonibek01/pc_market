//package uz.app.pc_market.controller.user.impl;
//
//
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestParam;
//import uz.app.pc_market.controller.user.test.UserCommentController;
//import uz.app.pc_market.dto.userdto.CommentRequestDTO;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import uz.app.pc_market.dto.userdto.ResponseMessage;
//import uz.app.pc_market.service.user.UserCommentService;
//
//import java.util.Collections;
//
//@Controller
//@RequiredArgsConstructor
//public class UserCommentControllerImpl implements UserCommentController {
//    private final UserCommentService userCommentService;
//
//    @Override
//    public String showCommentPage(@RequestParam("productId") Long productId, Model model, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            model.addAttribute("error", "User not logged in");
//            model.addAttribute("comments", Collections.emptyList());
//            model.addAttribute("productId", productId);
//            model.addAttribute("commentRequestDTO", new CommentRequestDTO());
//            return "sign-in";
//        }
//        ResponseMessage response = userCommentService.getAllComments(productId);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("comments", response.getData() != null ? response.getData() : Collections.emptyList());
//        model.addAttribute("productId", productId);
//        model.addAttribute("commentRequestDTO", new CommentRequestDTO());
//        return "comments";
//    }
//
//    @Override
//    public String addComment(@ModelAttribute CommentRequestDTO commentRequestDTO, Model model, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            model.addAttribute("error", "User not logged in");
//            model.addAttribute("comments", Collections.emptyList());
//            model.addAttribute("productId", commentRequestDTO.getProductId());
//            model.addAttribute("commentRequestDTO", commentRequestDTO);
//            return "sign-in";
//        }
//        ResponseMessage response = userCommentService.addUserComment(userId, commentRequestDTO);
//        model.addAttribute("success", response.getSuccess());
//        model.addAttribute("message", response.getMessage());
//        model.addAttribute("comments", userCommentService.getAllComments(commentRequestDTO.getProductId()).getData());
//        model.addAttribute("productId", commentRequestDTO.getProductId());
//        model.addAttribute("commentRequestDTO", new CommentRequestDTO());
//        return "comments";
//    }
//}