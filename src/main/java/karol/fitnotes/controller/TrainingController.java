package karol.fitnotes.controller;

import karol.fitnotes.domain.AppUser;
import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.AppUserRepo;
import karol.fitnotes.repository.ExerciseRepo;
import karol.fitnotes.repository.TrainingRepo;
import karol.fitnotes.service.TrainingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class TrainingController {

    private TrainingManager trainingManager;
    private TrainingRepo trainingRepo;
    private ExerciseRepo exerciseRepo;
    private AppUserRepo appUserRepo;

    @Autowired
    public TrainingController(TrainingManager trainingManager, TrainingRepo trainingRepo, ExerciseRepo exerciseRepo, AppUserRepo appUserRepo) {
        this.trainingManager = trainingManager;
        this.trainingRepo = trainingRepo;
        this.exerciseRepo = exerciseRepo;
        this.appUserRepo = appUserRepo;
    }



//////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/idTraining")
    public String getByID (@RequestParam("idTraining") Long id, Model model){
        model.addAttribute("training", trainingManager.getById(id));
        return "training";
    }

    /////////////ADD TRAINING/////////////////////////////////////
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getAddNewTrainingForm( Model model, Principal principal) {
        Training newTraining = new Training();
        Optional<AppUser> user = appUserRepo.findByUsername(principal.getName());
        model.addAttribute("newTraining", newTraining);
        model.addAttribute("userId", user.get().getId());
        return "addTraining";
    }

    @PostMapping("/new/{id}")
    public String save(@PathVariable("id") long id, @ModelAttribute("newTraining") Training training){
        if (training.getTrainingDate().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateTime = LocalDateTime.now().format(dateTimeFormatter);
            training.setTrainingDate(dateTime);
        }
        AppUser user = appUserRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        training.setAppUser(user);
        trainingManager.addTraining(training);
        return "redirect:/";
    }
    /////////////ADD TRAINING/////////////////////////////////////
    /////////////EDIT AND DELETE//////////////////////////////////
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Training training = trainingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("training", training);
        return "update-training";
    }

    @PostMapping("/update/{id}")
    public String updateTraining(@PathVariable("id") long id,Training training, BindingResult result, Model model,Principal principal) {
        if (result.hasErrors()) {
            training.setId(id);
            return "update-training";
        }
        if (training.getTrainingDate().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateTime = LocalDateTime.now().format(dateTimeFormatter);
            training.setTrainingDate(dateTime);
        }
        Training updateDate = trainingManager.getById(id);
        updateDate.setTrainingDate(training.getTrainingDate());
        trainingManager.addTraining(updateDate);

        model.addAttribute("training", trainingRepo.findAll());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Training training = trainingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (training.getExercises().isEmpty()){
            trainingRepo.delete(training);
            return "redirect:/";
        }else {
            for (Exercise exercise: training.getExercises())
             exerciseRepo.delete(exercise);
            trainingRepo.delete(training);
        }

        model.addAttribute("training", trainingRepo.findAll());
        return "redirect:/";
    }
    /////////////EDIT AND DELETE//////////////////////////////////

}
