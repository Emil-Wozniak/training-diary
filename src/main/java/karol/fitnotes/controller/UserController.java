package karol.fitnotes.controller;

import karol.fitnotes.domain.AppUser;
import karol.fitnotes.domain.Token;
import karol.fitnotes.repository.AppUserRepo;
import karol.fitnotes.repository.TokenRepo;
import karol.fitnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {
    private TokenRepo tokenRepo;
    private UserService userService;
    private AppUserRepo appUserRepo;

    @Autowired
    public UserController(TokenRepo tokenRepo, UserService userService, AppUserRepo appUserRepo) {
        this.tokenRepo = tokenRepo;
        this.userService = userService;
        this.appUserRepo = appUserRepo;
    }

    ////Do usunięcia
    @GetMapping("/hello")
    public String sayHello(Model model, Principal principal){

        model.addAttribute("hello", principal.getName());
        return "hello";
    }

    @GetMapping("/singup")
    public String getRegistrationForm (Model model){
        model.addAttribute("newUser", new AppUser());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(AppUser appUser){

        userService.addUser(appUser);

        return "registration";
    }

    @GetMapping("/token")
    public String confirmToken(@RequestParam String value){
        Token byValue = tokenRepo.findByValue(value);
        AppUser appUser = byValue.getAppUser();
        appUser.setEnable(true);
        appUserRepo.save(appUser);
        return "hello";
    }

}
