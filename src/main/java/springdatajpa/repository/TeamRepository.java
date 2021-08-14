package springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springdatajpa.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
