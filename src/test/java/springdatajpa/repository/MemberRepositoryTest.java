package springDataJpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import springDataJpa.domain.Member;
import springDataJpa.domain.Team;
import springDataJpa.dto.MemberDTO;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback()
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

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
    public void findByName2() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        List<Member> memberList = em.createNamedQuery("Member.findByName", Member.class)
                .setParameter("name", "memberA").getResultList();
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    public void findName() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        List<Member> memberList = memberRepository.findName("memberA");
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    public void findByNames() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        Member member3 = new Member("memberB");
        Member member4 = memberRepository.save(member3);

        List<Member> memberList = memberRepository.findByNames(Arrays.asList("memberA", "memberB"));
        for (Member m : memberList) {
            System.out.println(m.getName());
        }
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
    public void findByNameAndAge() {
        Member member = new Member("memberA", 10);
        Member member2 = memberRepository.save(member);

        Member member3 = new Member("memberA", 20);
        Member member4 = memberRepository.save(member3);

        List<Member> memberList = memberRepository.findByNameAndAge("memberA", 10);
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    public void findAll() {

        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.size()).isEqualTo(1);
    }


    @Test
    public void count() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);
        long count = memberRepository.count();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void findDTO() {
        Team team = new Team("teamA");
        Team team2 = teamRepository.save(team);

        Member member = new Member("memberA");
        member.setTeam(team2);
        Member member2 = memberRepository.save(member);

        List<MemberDTO> memberDTO = memberRepository.findMemberDTO();

        for (MemberDTO dto : memberDTO) {
            System.out.println(dto.getName());
            System.out.println(dto.getTeamName());
        }
    }

    //list 는 null이아님
    // SpringDataJpa를 사용하면, null Exception을 자동으로 처리
    @Test
    public void findNull() {
        Member member = new Member("memberA");
        Member member2 = memberRepository.save(member);

        Member member3 = memberRepository.findMemberByName("memberABC");
        //null
        System.out.println(member3);
        //0
        List<Member> memberList = memberRepository.findName("memberABC");
        System.out.println(memberList.size());

        //Member(id=1, name=memberA)
        Optional<Member> optionalMemberList = memberRepository.findOptionalByName("memberABC");
        System.out.println(optionalMemberList.orElseGet(() -> member));

    }

    @Test
    public void paging() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by("name").descending());

        //when
        //추가 count쿼리를 날려서 토탈카운트 등의 정보를 Page내부에 저장한다.
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //then
        List<Member> content = page.getContent();
        System.out.println("total count " + page.getTotalElements());
        System.out.println("total pages " + page.getTotalPages());
        System.out.println("current page " + page.getNumber());
        for (Member member : content) {
            System.out.println(member);
        }

        //Page상태로 DTO전환 가능
        Page<Member> page2 = memberRepository.findByAge2(age, pageRequest);
        Page<MemberDTO> pageDTO = page2.map(m -> new MemberDTO(m.getName(), m.getTeam().getName()));
        List<MemberDTO> content2 = pageDTO.getContent();

        //추가 count쿼리X, 요청한 데이터수보다 1개더 많은 데이터수를 가지고 옴(더보기 기능용)
        Slice<Member> page3 = memberRepository.findByAge3(age, pageRequest);
        System.out.println("current page " + page3.getNumber());

    }

    @Test
    public void bulkPlus() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 30));
        memberRepository.save(new Member("member4", 40));
        memberRepository.save(new Member("member5", 50));

        //when
        int count = memberRepository.bulkAgePlus(30);
        System.out.println("count : " + count);

        //then
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            System.out.println(member.getAge());
        }

    }
    
    @Test
    public void entityGraph() throws Exception {
       
        //given
        Team team = new Team("teamA");
        Team team2 = teamRepository.save(team);

        Member member = new Member("memberA");
        member.setTeam(team);
        Member member2 = memberRepository.save(member);

        Member member3 = new Member("memberA");
        member3.setTeam(team);
        Member member4 = memberRepository.save(member3);
        
        //when
        List<Member> memberList = memberRepository.findGraphByName("memberA");

        //then
        for (Member m : memberList) {
            System.out.println(m);
            System.out.println("createdDate" + m.getCreatedDate());
            System.out.println(m.getLastModifiedBy());
            System.out.println(m.getCreatedDate2());
        }
        
    }

}