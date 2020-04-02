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

    public Exercise getById(Long index){
        Exercise exerciseById=null;
        for (Exercise exercise:exerciseRepo.findAll()){
            if (exercise.getId().equals(index)){
                exerciseById = exercise;
                break;
            }
        }
        return exerciseById;
    }
}
