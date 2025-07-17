package uz.app.pc_market.controller.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auth/user/history")
public interface UserHistoryController {
    @GetMapping
    String showHistoryPage(Model model, HttpSession session);
}
