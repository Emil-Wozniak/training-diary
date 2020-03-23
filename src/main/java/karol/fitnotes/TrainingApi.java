package karol.fitnotes;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.domain.User;
import karol.fitnotes.repository.TrainingRepo;
import karol.fitnotes.service.ExerciseService;
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

    @Autowired
    public TrainingApi(TrainingManager trainingManager, TrainingRepo trainingRepo) {
        this.trainingManager = trainingManager;
         this.trainingRepo = trainingRepo;;
    }
//////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/all")
    public String getAll(Model model){
        model.addAttribute("training", trainingManager.getAll());
        return "index";
    }

    @GetMapping("/id")
    public String getByID (@RequestParam("id") Long id, Model model){
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
        return "index";
    }
}
