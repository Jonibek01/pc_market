package uz.app.pc_market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public interface AuthController {
    @GetMapping()
    String home(Model model);
}
