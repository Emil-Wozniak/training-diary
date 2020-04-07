package karol.fitnotes.domain;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Bmi {

    @Digits(integer = 8, fraction = 2)
    private double weight;
    @Digits(integer = 8, fraction = 2)
    private  double height;
}
