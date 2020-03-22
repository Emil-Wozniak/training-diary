package karol.fitnotes.service;

import karol.fitnotes.domain.Exercise;
import karol.fitnotes.domain.Training;
import karol.fitnotes.repository.TrainingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingManager {

    private TrainingRepo trainingRepo;

    @Autowired
    public TrainingManager(TrainingRepo trainingRepo) {
        this.trainingRepo = trainingRepo;
    }


    public Iterable<Training> getAll(){

        return trainingRepo.findAll();
    }

    public Training addTraining(Training training){
        return trainingRepo.save(training);
    }

    public void deleteTraining(Long id){
        trainingRepo.deleteById(id);
    }

    public Training getById(Long index){
        Training trainingById=null;
        for (Training training:trainingRepo.findAll()){
            if (training.getId().equals(index)){
                trainingById = training;
                break;
            }
        }
        return trainingById;
    }


}
