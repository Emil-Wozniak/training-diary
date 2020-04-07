package karol.fitnotes.repository;

import karol.fitnotes.domain.AppUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    AppUser findByUsernameOrderByTrainingsDesc(String lastName);
}
