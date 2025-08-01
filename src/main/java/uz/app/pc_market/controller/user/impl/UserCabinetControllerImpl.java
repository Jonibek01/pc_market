package uz.app.pc_market.controller.user.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import uz.app.pc_market.controller.user.UserCabinetController;
import uz.app.pc_market.repository.userrepo.UserRepository;

@Controller
@RequiredArgsConstructor
public class UserCabinetControllerImpl implements UserCabinetController {
    private final UserRepository userRepository;

    @Override
    public String userCabinet(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "User not logged in");
            return "sign-in";
        }
        userRepository.findById(userId).ifPresentOrElse(
                user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("balance", user.getBalance());
                },
                () -> model.addAttribute("error", "User not found")
        );
        return "user/user-cabinet";
    }
}