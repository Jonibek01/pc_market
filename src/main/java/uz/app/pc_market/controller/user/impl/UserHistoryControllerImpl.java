package uz.app.pc_market.controller.user.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import uz.app.pc_market.controller.user.UserHistoryController;
@Controller
public class UserHistoryControllerImpl implements UserHistoryController {
    @Override
    public ResponseEntity<?> getAllMyHistory(Long userId) {
        return null;
    }
}
