package uz.app.pc_market.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user-cabinet")
public interface UserCabinetController {
    @GetMapping
    String userCabinet();

    @GetMapping("/card-cabinet")
    String cardCabinet();
}
