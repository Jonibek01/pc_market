package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.History;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.repository.userrepo.HistoryRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserHistoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public ResponseMessage getUserHistory(Long userId) {
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
                    .message("You cannot view another user's history")
                    .data(null)
                    .build();
        }
        List<History> histories = historyRepository.findByUserId(userId);
        if (histories.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("No purchase history found")
                    .data(null)
                    .build();
        }
        return ResponseMessage.builder()
                .success(true)
                .message("Purchase history retrieved successfully")
                .data(histories)
                .build();
    }

    public void saveHistory(History history) {
        historyRepository.save(history);
    }

    private Long getCurrentUserId() {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("User not logged in");
        }
        return userId;
    }
}