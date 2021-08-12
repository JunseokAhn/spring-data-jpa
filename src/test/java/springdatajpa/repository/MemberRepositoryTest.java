package springdatajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import springdatajpa.domain.Member;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);

        Member member3 = memberRepository.findById(member2.getId()).get();

        assertThat(member2.getId()).isEqualTo(member3.getId());
        assertThat(member2).isEqualTo(member3);

    }
}