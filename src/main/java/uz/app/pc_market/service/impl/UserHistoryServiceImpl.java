package uz.app.pc_market.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.ResponseMessage;
import uz.app.pc_market.service.UserHistoryService;

@Service
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {
    @Override
    public ResponseMessage getUserHistory(Long userId) {
        return null;
    }
}
