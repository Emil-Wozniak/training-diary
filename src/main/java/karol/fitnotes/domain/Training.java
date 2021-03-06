package karol.fitnotes.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @Size(min = 0, max = 20)
    private String trainingDate;
    @OneToMany(mappedBy = "training")
    private List<Exercise> exercises;
    @ManyToOne
    private AppUser appUser;

    public Training(String trainingDate) {
        this.trainingDate = trainingDate;
    }
}
