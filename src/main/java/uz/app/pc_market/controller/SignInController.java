package uz.app.pc_market.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/sign-in")
public interface SignInController {
    @GetMapping
    String showSignInPage();

    @PostMapping
    String signIn(@RequestParam("username") String username,
                  @RequestParam("password") String password,
                  Model model, HttpSession session);
}
