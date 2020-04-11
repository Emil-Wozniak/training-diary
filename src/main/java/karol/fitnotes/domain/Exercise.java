package karol.fitnotes.domain;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Indexed;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Value
@Getter
@ToString
@Indexed
@Table(name = "exercise")
@NoArgsConstructor(force = true)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;

    @Size(min = 3, max = 20)
    String name;
    
    @Range(max = 200)
    double weight;
    
    @Range(max = 100)
    int rep; // TODO not self explanatory
    
    @Range(max = 20)
    int set; // TODO not self explanatory
    
    @JsonIgnore // in the case of been persisted TODO assert fasterxml is in dependencies
    @ManyToOne
    Training training;
}
