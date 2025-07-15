package uz.app.pc_market.service;

import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.ResponseMessage;

@Service
public interface UserHistoryService {
    ResponseMessage getUserHistory(Long userId);
}
