package uz.app.pc_market.controller.user;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/history")
public interface UserHistoryController {
    @GetMapping("/histories")
    String showHistoryPage(Model model, HttpSession session);
}
