package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.app.pc_market.entity.History;

@Component
public interface UserHistoryService {
    String getUserHistory(Long userId, Model model);
    void saveHistory(History history,Model model);
}
