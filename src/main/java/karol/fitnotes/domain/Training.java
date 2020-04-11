package karol.fitnotes.domain;

import lombok.*;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Value
@Getter
@ToString
@Indexed
@Table(name = "training")
@NoArgsConstructor(force = true)
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NonNull
    @Column(name = "training_date", nullable = false)
    @Size(min = 0, max = 20)
    String trainingDate; // why not Instant?
    
    @Singular("exercise") // you get method addExcercise()
    @OneToMany(
        mappedBy = "training", 
        cascade = CascadeType.ALL, // all type of operations
        fetch = FetchType.LAZY, // fetch on demand in repository
        orphanRemoval = true) // you may want to remove all related reconds when instance will be deleted 
    @JsonIgnoreProperties("training") // to prevent fetch this field in child instance
    List<Exercise> exercises;
    
    @ManyToOne
    @JsonIgnoreProperties("training") // to prevent fetch this field in child instance
    AppUser appUser;

//     public Training(String trainingDate) { I don't see a reason to do this
//         this.trainingDate = trainingDate; 
//     } 
}
