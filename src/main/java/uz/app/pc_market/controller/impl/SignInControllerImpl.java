package uz.app.pc_market.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.pc_market.controller.SignInController;
import uz.app.pc_market.controller.SignUpController;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.entity.enums.Role;
import uz.app.pc_market.repository.AuthRepository;

import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
public class SignInControllerImpl implements SignInController {
    final AuthRepository authRepository;

    @Override
    public String showSignInPage() {
        return "sign-in"; // sign-in.html fayli bo'lishi kerak
    }

    @Override
    public String signIn(@RequestParam("email") String email,
                         @RequestParam("password") String password,
                         Model model) {
        Optional<User> userByEmailAndPassword = authRepository.findUserByEmailAndPassword(email, password);
        if (userByEmailAndPassword.isEmpty()) {
            model.addAttribute("message", "user not found!");
            return "error";
        }
        User user = userByEmailAndPassword.get();
        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            if (user.getRole().equals(Role.SELLER)) {
                return "redirect:/seller-cabinet";
            } else if (user.getRole().equals(Role.USER)) {
                return "redirect:/user-cabinet";
            } else {
                return "redirect:/admin-cabinet";
            }
        } else {
            model.addAttribute("error", "Login yoki parol xato!");
            return "sign-in";
        }
    }
}
