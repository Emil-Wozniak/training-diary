package karol.fitnotes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage (){
        return "login";
    }

    @PostMapping("/login")
    public String showLoginFormFail(Model model) {
        model.addAttribute("error", "error");
        return "login";
    }
}
