package karol.fitnotes.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
public class Bmi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private double weight;
    private  double height;
}
