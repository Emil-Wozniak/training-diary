package karol.fitnotes.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Size(min = 3, max = 20)
    private String name;
    @Range(max = 200)
    private double weight;
    @Range(max = 100)
    private int rep;
    @Range(max = 20)
    private int set;
    @ManyToOne
    private Training training;


}
