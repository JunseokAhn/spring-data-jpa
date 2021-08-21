package springDataJpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springDataJpa.domain.Member;
import springDataJpa.dto.MemberDTO;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    //  메소드명을 자동 매칭
    List<Member> findByName(String name);

    List<Member> findTop3ByName(String name);

    Member findMemberByName(String name);

    Optional<Member> findOptionalByName(String memberABC);

    //  간단한 쿼리는 쉽게 구현가능한 장점이 있지만, 무한히 길어진다는 단점
    List<Member> findByNameAndAgeGreaterThan(String name, int age);

    //  직접 엔티티에 쿼리를 치므로 메소드명이 길어지진않지만, 엔티티가 지저분해진다는 단점
    @Query(name = "Member.findByName")
    List<Member> findName(@Param("name") String name);

    // 메소드명 자동 매칭 cancel, 레포지토리에 직접 쿼리 입력
    @Query("select m from Member m where m.name = :name and m.age > :age")
    List<Member> findByNameAndAge(@Param("name") String name, @Param("age") int age);

    @Query("select new springDataJpa.dto.MemberDTO(m.name, t.name) from Member m join m.team t")
    List<MemberDTO> findMemberDTO();

    @Query("select m from Member m where name in :name")
    List<Member> findByNames(@Param("name") List<String> names);

    Page<Member> findByAge(int age, Pageable pageable);


    //countQuery를 사용하여, 기존에 select한 결과값에서 row수를 totalCount로 계산한다.
    @Query(value = "select m from Member m where age = :age",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge2(@Param("age") int age, Pageable pageable);


    @Query(value = "select m from Member m where age = :age")
    Slice<Member> findByAge3(@Param("age") int age, Pageable pageable);
}
