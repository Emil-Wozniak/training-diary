package karol.fitnotes.repository;

import karol.fitnotes.domain.Bmi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BmiRepo extends JpaRepository<Bmi, Long> {
}
