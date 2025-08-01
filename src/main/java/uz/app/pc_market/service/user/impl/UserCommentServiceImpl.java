package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.entity.Comment;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.repository.userrepo.CommentRepository;
import uz.app.pc_market.repository.userrepo.UserProductRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserCommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommentServiceImpl implements UserCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserProductRepository productRepository;
    private final HttpSession session;

    @Override
    public String addUserComment(Long userId, CommentRequestDTO commentRequestDTO, Model model) {
        Long currentUserId = getCurrentUserId();
        log.info("Adding comment for userId: {} and productId: {}", userId, commentRequestDTO.getProductId());
        if (currentUserId == null) {
            model.addAttribute("error", "Please log in to add a comment");
            return "sign-in";
        }
        if (!userId.equals(currentUserId)) {
            model.addAttribute("error", "You cannot add a comment for another user");
            return "error";
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error";
        }

        Optional<Product> productOptional = productRepository.findById(commentRequestDTO.getProductId());
        if (productOptional.isEmpty()) {
            model.addAttribute("error", "Product not found");
            return "error";
        }

        if (commentRequestDTO.getDescription() == null || commentRequestDTO.getDescription().isEmpty()) {
            model.addAttribute("error", "Comment description is required");
            model.addAttribute("commentDto", commentRequestDTO);
            model.addAttribute("products", productRepository.findAll());
            return "user/comment/add-comment";
        }

        if (commentRequestDTO.getRating() == null || commentRequestDTO.getRating() < 0 || commentRequestDTO.getRating() > 5) {
            model.addAttribute("error", "Rating must be between 0 and 5");
            model.addAttribute("commentDto", commentRequestDTO);
            model.addAttribute("products", productRepository.findAll());
            return "user/comment/add-comment";
        }

        Comment comment = new Comment();
        comment.setDescription(commentRequestDTO.getDescription());
        comment.setRating(commentRequestDTO.getRating());
        comment.setUser(userOptional.get());
        comment.setProduct(productOptional.get());

        try {
            commentRepository.save(comment);
            model.addAttribute("success", "Comment added successfully");
        } catch (Exception e) {
            log.error("Failed to save comment for userId: {}", userId, e);
            model.addAttribute("error", "Failed to save comment");
            return "user/comment/add-comment";
        }

        return "redirect:/user/comment/add-comment";
    }

    @Override
    public String getAllComments(Long productId, Model model) {
        log.info("Fetching comments for productId: {}", productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            model.addAttribute("error", "Product not found");
            return "error";
        }

        List<Comment> comments = commentRepository.findByProductId(productId);
        System.out.println("Found comments: " + comments.size());
        if (comments.isEmpty()) {
            model.addAttribute("message", "No comments found for this product. Be the first to comment!");
        } else {
            model.addAttribute("comments", comments);
            model.addAttribute("product", productOptional.get());
        }

        return "user/comment/comments";
    }

    @Override
    public Double calculateAverageRating(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0.0;
        }
        double sum = comments.stream()
                .filter(comment -> comment.getRating() != null)
                .mapToDouble(Comment::getRating)
                .average()
                .orElse(0.0);
        return Math.round(sum * 100.0) / 100.0;
    }

    @Override
    public List<Comment> getCommentsByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }

    private Long getCurrentUserId() {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userId;
    }
}