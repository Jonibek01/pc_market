package uz.app.pc_market.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.pc_market.controller.SignUpController;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.entity.enums.Role;
import uz.app.pc_market.repository.AuthRepository;

@Controller
@RequiredArgsConstructor
public class SignUpControllerImpl implements SignUpController {

    final AuthRepository authRepository;

    @Override
    public String showSignUpPage() {
        return "sign-up";
    }

    @Override
    public String signUp(@RequestParam("name") String name,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("balance") Double balance,
                         Model model) {

        if (authRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Bu email allaqachon mavjud!");
            return "sign-up";
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .balance(balance)
                .role(Role.USER)
                .enabled(true)
                .build();

        authRepository.save(user);
        System.out.println("user successfully saved " + user);
        return "redirect:/sign-in";
    }
}
