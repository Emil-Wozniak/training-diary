package karol.fitnotes.service;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.ExerciseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private ExerciseRepo exerciseRepo;

    @Autowired
    public ExerciseService(ExerciseRepo exerciseRepo) {
        this.exerciseRepo = exerciseRepo;
    }

    public List<Exercise> getAll(){
        return exerciseRepo.findAll();
    }

    public Exercise getById(Long id){
        return exerciseRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public void deleteExercise (Exercise exercise) {
        exerciseRepo.delete(exercise);
    }

    public void addExercise (Exercise exercise){
        exerciseRepo.save(exercise);
    }

    public void deleteExerciseById (Long id){
        if (exerciseRepo.existsById(id)){
            exerciseRepo.deleteById(id);
        }
    }

    public Exercise updateExercise (Long id, Exercise exercise){
        Exercise updateExercise = exerciseRepo.findById(id)
                .map(element -> {
                    element.setName(exercise.getName());
                    element.setSet(exercise.getSet());
                    element.setRep(exercise.getRep());
                    element.setWeight(exercise.getWeight());
                    return exerciseRepo.save(element);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return updateExercise;
    }
}
