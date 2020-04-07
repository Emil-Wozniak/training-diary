package karol.fitnotes.controller;

import karol.fitnotes.domain.Bmi;
import karol.fitnotes.service.BmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class HomeController {

    private BmiService bmiService;

    @Autowired
    public HomeController(BmiService bmiService) {
        this.bmiService = bmiService;
    }

    @GetMapping
    public String homePage(){
        return "home";
    }
    
    @GetMapping("/bmi")
    public String getFormBmi(Model model) {
        Bmi result = new Bmi();
        model.addAttribute("newBmi", result);
        return "bmi";
    }

    @PostMapping("/bmi")
    public String getBmi(@Valid @ModelAttribute("newBmi") Bmi bmi, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            return "bmi";
        }
        double bmiResult = bmiService.getBmi(bmi.getWeight(), bmi.getHeight());

        model.addAttribute("all", bmiResult);
        return "bmi";
    }
}
