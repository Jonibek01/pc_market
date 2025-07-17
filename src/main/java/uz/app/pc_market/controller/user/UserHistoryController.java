package uz.app.pc_market.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auth/user/history")
public interface UserHistoryController {
    @GetMapping("/read")
    ResponseEntity<?> getAllMyHistory(@RequestParam Long userId);
}
