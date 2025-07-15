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
        return "sign-up"; // templates/sign-up.html bo'lishi kerak
    }

    @Override
    public String signUp(@RequestParam("fullname") String fullName,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         Model model) {


        if (authRepository.findUserByEmailAndPassword(email, password).isPresent()) {
            model.addAttribute("error", "Bu email allaqachon mavjud");
            return "sign-up";
        }


        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(Role.USER); // Default role

        authRepository.save(newUser);

        return "redirect:/sign-in";
    }
}
