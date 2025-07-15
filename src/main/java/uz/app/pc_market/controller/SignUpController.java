package uz.app.pc_market.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sign-up")
public interface SignUpController {

    @GetMapping
    String showSignUpPage();

    @PostMapping
    String signUp(@RequestParam("fullname") String fullName,
                  @RequestParam("email") String email,
                  @RequestParam("password") String password,
                  Model model);
}
