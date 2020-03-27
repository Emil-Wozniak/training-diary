package karol.fitnotes;

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
public class TrainingApi {

    private TrainingManager trainingManager;
    private TrainingRepo trainingRepo;
    private ExerciseRepo exerciseRepo;

    @Autowired
    public TrainingApi(TrainingManager trainingManager, TrainingRepo trainingRepo, ExerciseRepo exerciseRepo) {
        this.trainingManager = trainingManager;
         this.trainingRepo = trainingRepo;
         this.exerciseRepo = exerciseRepo;
    }
//////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/allTraining")
    public String getAll(Model model){
        model.addAttribute("training", trainingManager.getAll());
        return "index";
    }

    @GetMapping("/idTraining")
    public String getByID (@RequestParam("idTraining") Long id, Model model){
        model.addAttribute("training", trainingManager.getById(id));
        return "index";
    }

    /////////////ADD TRAINING/////////////////////////////////////
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getAddNewProductForm(Model model) {
        Training newTraining = new Training();
        model.addAttribute("newTraining", newTraining);
        return "addTraining";
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("newTraining") Training training){
        trainingManager.addTraining(training);
        return "redirect:/all";
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
    public String updateUser(@PathVariable("id") long id,Training training, BindingResult result, Model model) {
        if (result.hasErrors()) {
            training.setId(id);
            return "update-training";
        }
        trainingRepo.save(training);
        model.addAttribute("training", trainingRepo.findAll());
        return "redirect:/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Training user = trainingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        trainingRepo.delete(user);
        model.addAttribute("training", trainingRepo.findAll());
        return "index";
    }
    /////////////EDIT AND DELETE//////////////////////////////////

}
