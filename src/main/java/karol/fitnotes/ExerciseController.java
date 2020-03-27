package karol.fitnotes;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.ExerciseRepo;
import karol.fitnotes.repository.TrainingRepo;
import karol.fitnotes.service.TrainingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/all")
    public String getAllExercises(Model model){
        Training training1 = null;
        model.addAttribute("exercises", exerciseRepo.findAll());
        model.addAttribute("training2", trainingRepo.findAll());
        return "index";
    }

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
        model.addAttribute("Exercise", newExercise);
        model.addAttribute("trainingId", training);
        return "addExercise";
    }

    @PostMapping("/add-exercise/{id}")
    public String saveEx(@PathVariable("id") long id,@ModelAttribute("Exercise") Exercise exercise){
        Training t1 = trainingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        exercise.setTraining(t1);
        exerciseRepo.save(exercise);

        return "redirect:/all";
    }

}
