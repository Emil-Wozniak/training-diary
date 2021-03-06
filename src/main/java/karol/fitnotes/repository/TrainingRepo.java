package karol.fitnotes.repository;

import karol.fitnotes.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {
    
}
