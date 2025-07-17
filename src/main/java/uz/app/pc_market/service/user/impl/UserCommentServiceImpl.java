package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.CommentRequestDTO;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.Comment;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.repository.userrepo.CommentRepository;
import uz.app.pc_market.repository.userrepo.ProductRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserCommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCommentServiceImpl implements UserCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HttpSession session;

    @Override
    public ResponseMessage addUserComment(Long userId, CommentRequestDTO commentRequestDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        if (!userOptional.get().getId().equals(getCurrentUserId())) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You cannot add a comment for another user")
                    .data(null)
                    .build();
        }
        Optional<Product> productOptional = productRepository.findById(commentRequestDTO.getProductId());
        if (productOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Product not found")
                    .data(null)
                    .build();
        }
        if (commentRequestDTO.getDescription() == null || commentRequestDTO.getDescription().isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Comment description is required")
                    .data(null)
                    .build();
        }
        if (commentRequestDTO.getRating() == null || commentRequestDTO.getRating() < 0 || commentRequestDTO.getRating() > 5) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Rating must be between 0 and 5")
                    .data(null)
                    .build();
        }
        Comment comment = new Comment();
        comment.setDescription(commentRequestDTO.getDescription());
        comment.setRating(commentRequestDTO.getRating());
        comment.setUser(userOptional.get());
        comment.setProduct(productOptional.get());
        Comment savedComment = commentRepository.save(comment);
        return ResponseMessage.builder()
                .success(true)
                .message("Comment added successfully")
                .data(savedComment)
                .build();
    }

    @Override
    public ResponseMessage getAllComments(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Product not found")
                    .data(null)
                    .build();
        }
        List<Comment> comments = commentRepository.findByProductId(productId);
        if (comments.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("No comments found for this product")
                    .data(null)
                    .build();
        }
        return ResponseMessage.builder()
                .success(true)
                .message("Comments retrieved successfully")
                .data(comments)
                .build();
    }

    private Long getCurrentUserId() {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("User not logged in");
        }
        return userId;
    }
}