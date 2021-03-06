package karol.fitnotes.controller;

import karol.fitnotes.domain.AppUser;
import karol.fitnotes.repository.AppUserRepo;
import karol.fitnotes.repository.TokenRepo;
import karol.fitnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/trainings")
    public String allTrainings(Model model, Principal principal){
        model.addAttribute("appUsers", userService.getAllTrainings(principal.getName()));
        return "index";
    }

    @GetMapping("/singup")
    public String getRegistrationForm (Model model){
        model.addAttribute("newUser", new AppUser());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(@Valid @ModelAttribute("newUser") AppUser appUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.addUser(appUser);

        return "registration";
    }

    @GetMapping("/token")
    public String confirmToken(@RequestParam String value){
        userService.confirmToken(value);
        return "redirect:/trainings";
    }

}
