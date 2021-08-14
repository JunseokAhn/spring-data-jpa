package springdatajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import springdatajpa.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findById() {

        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        Member member3 = memberRepository.findById(member2.getId()).get();
        assertThat(member2).isEqualTo(member3);
    }

    @Test
    public void findByName() {

        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        List<Member> memberList = memberRepository.findByName(member.getName());
        assertThat(member2).isEqualTo(memberList.get(0));
    }

    @Test
    public void findByNameAndAgeGreaterThan() {

        Member member = new Member("memberA", 10);
        Member member2 = memberRepository.save(member);

        Member member3 = new Member("memberA", 20);
        Member member4 = memberRepository.save(member3);

        List<Member> memberList = memberRepository.findByNameAndAgeGreaterThan("memberA", 10);
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    public void findAll() {

        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).isEqualTo(1);
    }


    @Test
    public void count() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        long count = memberRepository.count();
        assertThat(count).isEqualTo(1);
    }
}