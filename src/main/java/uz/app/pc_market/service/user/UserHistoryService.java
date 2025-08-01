package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.History;
import uz.app.pc_market.entity.UserHistory;

import java.util.List;

@Component
public interface UserHistoryService {
    String getUserHistory(Model model);
    List<UserHistory> findByUserId(Long userId);
}
