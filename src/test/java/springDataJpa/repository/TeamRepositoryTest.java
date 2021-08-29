package springDataJpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springDataJpa.domain.Team;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void findById() {

        Team team = new Team("teamA");
        Team team2 = teamRepository.save(team);
        Team team3 = teamRepository.findByName("teamA");
        assertThat(team2).isEqualTo(team3);
    }


}