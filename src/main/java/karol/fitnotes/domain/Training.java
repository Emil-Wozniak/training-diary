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

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String trainingDate;
    @OneToMany(mappedBy = "training")
    private List<Exercise> exercises;

    public Training(String trainingDate) {
        this.trainingDate = trainingDate;
    }
}
