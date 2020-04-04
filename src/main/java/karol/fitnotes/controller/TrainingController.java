package karol.fitnotes.controller;

import karol.fitnotes.domain.AppUser;
import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.AppUserRepo;
import karol.fitnotes.repository.ExerciseRepo;
import karol.fitnotes.repository.TrainingRepo;
import karol.fitnotes.service.ExerciseService;
import karol.fitnotes.service.TrainingManager;
import karol.fitnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/trainings")
public class TrainingController {

    private TrainingManager trainingManager;
    private ExerciseService exerciseService;
    private UserService userService;

    @Autowired
    public TrainingController(TrainingManager trainingManager, ExerciseService exerciseService, UserService userService) {
        this.trainingManager = trainingManager;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }


//////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/idTraining")
    public String getByID(@RequestParam("idTraining") Long id, Model model) {
        model.addAttribute("training", trainingManager.getById(id));
        return "training";
    }

    /////////////ADD TRAINING/////////////////////////////////////
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getAddNewTrainingForm(Model model, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        model.addAttribute("newTraining", new Training());
        model.addAttribute("userId", user.getId());
        return "addTraining";
    }

    @PostMapping("/new/{id}")
    public String save(@PathVariable("id") long id, @ModelAttribute("newTraining") Training training, BindingResult result) {
        if (result.hasErrors()) {
            return "update-training";
        }
        trainingManager.setCurrentDateIfFieldIsEmpty(training);
        AppUser user = userService.getById(id);
        training.setAppUser(user);
        trainingManager.addTraining(training);
        return "redirect:/";
    }

    /////////////ADD TRAINING/////////////////////////////////////
    /////////////EDIT AND DELETE//////////////////////////////////
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Training training = trainingManager.getById(id);
        model.addAttribute("training", training);
        return "update-training";
    }

    @PostMapping("/update/{id}")
    public String updateTraining(@PathVariable("id") long id, Training training, BindingResult result) {
        if (result.hasErrors()) {
            training.setId(id);
            return "update-training";
        }
        trainingManager.setCurrentDateIfFieldIsEmpty(training);
        trainingManager.updateTraining(id, training);
        return "training";
    }

    @GetMapping("/delete/{id}")
    public String deleteTraining(@PathVariable("id") Long id) {
        trainingManager.deleteTrainingById(id);
        return "redirect:/";
    }
    /////////////EDIT AND DELETE//////////////////////////////////

}
