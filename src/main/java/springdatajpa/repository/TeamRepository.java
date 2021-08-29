package springDataJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springDataJpa.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
}
