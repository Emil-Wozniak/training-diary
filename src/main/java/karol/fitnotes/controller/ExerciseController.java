package karol.fitnotes.controller;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.service.ExerciseService;
import karol.fitnotes.service.TrainingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/add-exercise/{id}", method = RequestMethod.GET)
    public String showAddExerciseForm(@PathVariable("id") long id, Model model) {
        Exercise newExercise = new Exercise();
        Training training = trainingManager.getById(id);
        model.addAttribute("exercise", newExercise);
        model.addAttribute("trainingId", training);
        return "addExercise";
    }

    @PostMapping("/add-exercise/{id}")
    public String saveExercise(@PathVariable("id") long id, @ModelAttribute("Exercise") Exercise exercise, BindingResult result) {
        Training trainingById = trainingManager.getById(id);
        exercise.setTraining(trainingById);
        exerciseService.addExercise(exercise);

        return "redirect:/trainings/idTraining?idTraining=" + id;
    }

    /////////////DELETE/////////////////////
    @GetMapping("/delete-exercise/{id}")
    public String deleteExercise(@PathVariable("id") long id) {
        exerciseService.deleteExerciseById(id);
        return "redirect:/trainings";
    }

    ///////////edit/
    @GetMapping("/edit-exercise/{id}")
    public String showExerciseUpdateForm(@PathVariable("id") long id, Model model) {
        Exercise exercise = exerciseService.getById(id);
        model.addAttribute("exercises", exercise);
        return "update-exercise";
    }

    @PostMapping("/update-exercise/{id}")
    public String updateExercise(@PathVariable("id") long id, Exercise exercise, Model model) {
        exerciseService.updateExercise(id, exercise);
        return "redirect:/trainings";
    }
}
