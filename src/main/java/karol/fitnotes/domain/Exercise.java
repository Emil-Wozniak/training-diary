package karol.fitnotes.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private double weight;
    private int rep;
    private int set;


}
