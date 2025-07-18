package uz.app.pc_market.service.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.History;

@Component
public interface UserHistoryService {
    ResponseMessage getUserHistory(Long userId);

    void saveHistory(History history);
}
