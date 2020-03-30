package karol.fitnotes.service;

import karol.fitnotes.domain.Bmi;
import karol.fitnotes.repository.BmiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BmiService {

    private BmiRepo bmiRepo;

    @Autowired
    public BmiService(BmiRepo bmiRepo) {
        this.bmiRepo = bmiRepo;
    }

    public double getBmi(double weight, double height){
        double result = 0;
        double powerHeight = Math.pow(height/100,2);
        result = weight/powerHeight;
        result *=100;
        result = Math.round(result);
        result /=100;
        return result;
    }
}
