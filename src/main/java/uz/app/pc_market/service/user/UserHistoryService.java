package uz.app.pc_market.service.user;

import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ResponseMessage;

@Service
public interface UserHistoryService {
    ResponseMessage getUserHistory(Long userId);
}
