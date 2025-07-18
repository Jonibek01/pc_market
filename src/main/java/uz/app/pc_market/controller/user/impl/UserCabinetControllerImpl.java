package uz.app.pc_market.controller.user.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.app.pc_market.controller.user.UserCabinetController;
@Controller
public class UserCabinetControllerImpl implements UserCabinetController {
    public String userCabinet() {
        return "user/user-cabinet";
    }
}
