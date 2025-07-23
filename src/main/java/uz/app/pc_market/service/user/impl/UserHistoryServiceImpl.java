package uz.app.pc_market.service.user.impl;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.History;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.entity.UserHistory;
import uz.app.pc_market.repository.userrepo.UserHistoryRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserHistoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserHistoryServiceImpl implements UserHistoryService {
    private final UserHistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public String getUserHistory(Long userId, Model model) {
        if (userId == null) {
            log.warn("User ID is null in getUserHistory");
            model.addAttribute("error", "Invalid user ID");
            return "sign-in";
        }
        List<UserHistory> histories = historyRepository.findByUserId(userId);
        if (histories.isEmpty()) {
            log.info("No purchase history found for userId {}", userId);
            model.addAttribute("error", "No purchase history available");
            return "user/history/histories";
        }
        model.addAttribute("histories", histories);
        log.info("Fetched {} history records for userId {}", histories.size(), userId);
        return "user/history/histories";
    }
    @Override
    public List<UserHistory> findByUserId(Long userId) {
        return historyRepository.findByUserId(userId);
    }
}