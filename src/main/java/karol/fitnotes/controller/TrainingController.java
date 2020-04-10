package karol.fitnotes.controller;

import karol.fitnotes.domain.AppUser;
import karol.fitnotes.domain.Training;
import karol.fitnotes.service.TrainingManager;
import karol.fitnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingManager trainingManager;
    private UserService userService;

    @Autowired
    public TrainingController(TrainingManager trainingManager, UserService userService) {
        this.trainingManager = trainingManager;
        this.userService = userService;
    }

    @GetMapping("/idTraining")
    public String getByID(@RequestParam("idTraining") Long id, Model model) {
        model.addAttribute("training", trainingManager.getById(id));
        return "/training";
    }

    /////////////ADD TRAINING/////////////////////////////////////
    @GetMapping("/new")
    public String getAddNewTrainingForm( Model model, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        model.addAttribute("newTraining", new Training());
        model.addAttribute("userId", user.getId());
        return "/addTraining";
    }

    @PostMapping("/new/{id}")
    public String saveTraining(@PathVariable("id") long id,@Valid @ModelAttribute("newTraining") Training training,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/addTraining";
        }
        trainingManager.setCurrentDateIfFieldIsEmpty(training);
        AppUser user = userService.getById(id);
        training.setAppUser(user);
        trainingManager.addTraining(training);
        return "redirect:/trainings";
    }
    /////////////EDIT AND DELETE//////////////////////////////////
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("training", trainingManager.getById(id));
        return "/update-training";
    }

    @PostMapping("/update/{id}")
    public String updateTraining(@PathVariable("id") long id, Training training, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            training.setId(id);
            return "/update-training";
        }
        trainingManager.setCurrentDateIfFieldIsEmpty(training);
        trainingManager.updateTraining(id, training);
        return "/training";
    }

    @GetMapping("/delete/{id}")
    public String deleteTraining(@PathVariable("id") Long id) {
        trainingManager.deleteTrainingById(id);
        return "redirect:/trainings";
    }

}
