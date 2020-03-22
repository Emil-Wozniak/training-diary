package karol.fitnotes.repository;

import karol.fitnotes.domain.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepo extends CrudRepository<Training, Long> {

}
