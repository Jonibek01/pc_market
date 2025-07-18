package uz.app.pc_market.controller.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.user.UserHistoryController;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.service.user.UserHistoryService;

@Controller
@RequiredArgsConstructor
public class UserHistoryControllerImpl implements UserHistoryController {
    private final UserHistoryService userHistoryService;

    @Override
    public String showHistoryPage(Model model, HttpSession session) {
        User userId = (User) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        ResponseMessage response = userHistoryService.getUserHistory(userId.getId());
        model.addAttribute("success", response.getSuccess());
        model.addAttribute("message", response.getMessage());
        model.addAttribute("histories", response.getData());
        return "history";
    }
}
