package karol.fitnotes.controller;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.ExerciseRepo;
import karol.fitnotes.repository.TrainingRepo;
import karol.fitnotes.service.TrainingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExerciseController {

    private TrainingManager trainingManager;
    private TrainingRepo trainingRepo;
    private ExerciseRepo exerciseRepo;

    @Autowired
    public ExerciseController(TrainingManager trainingManager, TrainingRepo trainingRepo, ExerciseRepo exerciseRepo) {
        this.trainingManager = trainingManager;
        this.trainingRepo = trainingRepo;
        this.exerciseRepo = exerciseRepo;
    }

    ///////////////////GET ALL/////////////////////
    @GetMapping("/all")
    public String getAllExercises(Model model){

        model.addAttribute("exercises", exerciseRepo.findAll());
        model.addAttribute("training2", trainingRepo.findAll());
        return "index";
    }

    //////////////////GET BY ID///////////////////////////
    @GetMapping("/id")
    public String getExercisesByID (@RequestParam("id") Long id, Model model){
        model.addAttribute("exercises", exerciseRepo.findById(id));
        return "index";
    }
    /////////////Add exercise//////////////////////////////////
    @RequestMapping(value = "/add-exercise/{id}", method = RequestMethod.GET)
    public String showAddExerciseForm(@PathVariable("id") long id, Model model) {
        Exercise newExercise = new Exercise();
        Training training = trainingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("exercise", newExercise);
        model.addAttribute("trainingId", training);
        return "addExercise";
    }

    @PostMapping("/add-exercise/{id}")
    public String saveExercise(@PathVariable("id") long id,@ModelAttribute("Exercise") Exercise exercise){
        Training t1 = trainingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        exercise.setTraining(t1);
        exerciseRepo.save(exercise);

        return "redirect:/all";
    }

    /////////////DELETE/////////////////////
    @GetMapping("/delete-exercise/{id}")
    public String deleteExercise(@PathVariable("id") long id){

        exerciseRepo.deleteById(id);

        return "redirect:/all";
    }

    ///////////edit/
    @GetMapping("/edit-exercise/{id}")
    public String showExerciseUpdateForm(@PathVariable("id") long id, Model model) {
        Exercise exercise = exerciseRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("exercises", exercise);
        return "update-exercise";
    }

    @PostMapping("/update-exercise/{id}")
    public String updateExercise(@PathVariable("id") long id, Exercise exercise, BindingResult result, Model model) {
        if (result.hasErrors()) {
            exercise.setId(id);
            return "update-exercise";
        }
        exerciseRepo.save(exercise);
        model.addAttribute("exercises", exerciseRepo.findAll());
        return "redirect:/all";
    }
}
