package karol.fitnotes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Training {

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Long id;
    private String plecy;
    private String klatka;
    private String biceps;
    private String triceps;
    private String brzuch;
    private String trainingDate;

    public Training(String trainingDate, String plecy, String klatka, String biceps,String triceps, String brzuch) {
        this.trainingDate = trainingDate;
        this.plecy=plecy;
        this.klatka=klatka;
        this.triceps=triceps;
        this.biceps=biceps;
        this.brzuch=brzuch;
    }
}
