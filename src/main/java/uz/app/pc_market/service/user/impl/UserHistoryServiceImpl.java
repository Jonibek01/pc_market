package uz.app.pc_market.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.service.user.UserHistoryService;

@Service
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {
    @Override
    public ResponseMessage getUserHistory(Long userId) {
        return null;
    }
}
