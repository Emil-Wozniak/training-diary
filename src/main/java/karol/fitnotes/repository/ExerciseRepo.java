package karol.fitnotes.repository;

import karol.fitnotes.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepo extends JpaRepository<Exercise, Long> {
}
