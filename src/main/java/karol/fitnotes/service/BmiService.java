package karol.fitnotes.service;

import org.springframework.stereotype.Service;

@Service
public class BmiService {

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
