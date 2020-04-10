package karol.fitnotes.controller;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.service.ExerciseService;
import karol.fitnotes.service.TrainingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/trainings")
public class ExerciseController {

    private ExerciseService exerciseService;
    private TrainingManager trainingManager;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, TrainingManager trainingManager) {
        this.exerciseService = exerciseService;
        this.trainingManager = trainingManager;
    }

    /////////////Add exercise//////////////////////////////////
    @GetMapping("/add-exercise/{id}")
    public String showAddExerciseForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("newExercise", new Exercise());
        model.addAttribute("trainingId", trainingManager.getById(id));
        return "/addExercise";
    }

    @PostMapping("/add-exercise/{id}")
    public String saveExercise(@PathVariable("id") long id,@Valid @ModelAttribute("newExercise") Exercise exercise, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("trainingId", trainingManager.getById(id));
            return "/addExercise";
        }

        exercise.setTraining(trainingManager.getById(id));
        exerciseService.addExercise(exercise);
        return "redirect:/trainings/idTraining?idTraining=" + id;
    }

    /////////////DELETE/////////////////////
    @GetMapping("/delete-exercise/{id}")
    public String deleteExercise(@PathVariable("id") long id) {
        exerciseService.deleteExerciseById(id);
        return "redirect:/trainings";
    }

    ///////////edit/////////////////////
    @GetMapping("/edit-exercise/{id}")
    public String showExerciseUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("exercises", exerciseService.getById(id));
        return "/update-exercise";
    }

    @PostMapping("/update-exercise/{id}")
    public String updateExercise(@PathVariable("id") long id,@Valid @ModelAttribute("exercises") Exercise exercise,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/update-exercise";
        }
        exerciseService.updateExercise(id, exercise);
        return "redirect:/trainings";
    }
}
