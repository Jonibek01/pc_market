package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    public String getUserHistory(Long userId, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            model.addAttribute("error", "Please log in to view your purchase history");
            return "sign-in";
        }
        if (!userId.equals(currentUserId)) {
            model.addAttribute("error", "You cannot view another user's history");
            return "error";
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "error";
        }
        List<History> histories = historyRepository.findByUserId(userId);
        if (histories.isEmpty()) {
            model.addAttribute("error", "No purchase history found");
        } else {
            model.addAttribute("histories", histories);
        }
        return "user/history/histories";
    }

    @Override
    public void saveHistory(History history, Model model) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId == null) {
            model.addAttribute("error", "Please log in to save history");
            return;
        }
        if (history.getUser() == null || !history.getUser().getId().equals(currentUserId)) {
            model.addAttribute("error", "Invalid user for history");
            return;
        }
        if (history.getProduct() == null) {
            model.addAttribute("error", "Product is required for history");
            return;
        }
        historyRepository.save(history);
        model.addAttribute("success", "Purchase history saved successfully");
    }

    private Long getCurrentUserId() {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return null;
        }
        return userId;
    }
}