package karol.fitnotes.service;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.ExerciseRepo;
import karol.fitnotes.repository.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TrainingManager {

    private TrainingRepo trainingRepo;
    private ExerciseRepo exerciseRepo;

    @Autowired
    public TrainingManager(TrainingRepo trainingRepo, ExerciseRepo exerciseRepo) {
        this.exerciseRepo = exerciseRepo;
        this.trainingRepo = trainingRepo;
    }


    public Iterable<Training> getAll() {

        return trainingRepo.findAll();
    }

    public Training addTraining(Training training) {
        return trainingRepo.save(training);
    }

    public void deleteTrainingById(Long id) {
        Training trainingToDelete = trainingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (!trainingToDelete.getExercises().isEmpty()) {
            for (Exercise exercise : trainingToDelete.getExercises()) {
                exerciseRepo.delete(exercise);
            }
        }
        trainingRepo.delete(trainingToDelete);
    }

    public Training getById(Long id) {
        return trainingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public Training updateTraining(long id, Training training) {
        Training updateDate = trainingRepo.findById(id)
                .map(element -> {
                    element.setTrainingDate(training.getTrainingDate());
                    return trainingRepo.save(element);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return updateDate;
    }

    public String setCurrentDateIfFieldIsEmpty(Training training) {
        String currentDate = null;
        if (training.getTrainingDate().isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateTime = LocalDateTime.now().format(dateTimeFormatter);
            training.setTrainingDate(dateTime);
            currentDate = training.getTrainingDate();
        }
        return currentDate;
    }
}
