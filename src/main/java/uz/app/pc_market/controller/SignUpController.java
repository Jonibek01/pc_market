package uz.app.pc_market.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sign-up")
public interface SignUpController {

    @GetMapping
    String showSignUpPage();

    @PostMapping
    String signUp(@RequestParam("name") String fullName,
                  @RequestParam("email") String email,
                  @RequestParam("password") String password,
                  @RequestParam("balance") Double balance,
                  Model model);
}
