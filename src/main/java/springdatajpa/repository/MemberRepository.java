package springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springdatajpa.domain.Member;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {

    //  메소드명을 자동 매칭
    List<Member> findByName(String name);

    //  간단한 쿼리는 쉽게 구현가능한 장점이 있지만, 무한히 길어진다는 단점
    List<Member> findByNameAndAgeGreaterThan(String name, int age);

    //  직접 엔티티에 쿼리를 치므로 메소드명이 길어지진않지만, 엔티티가 지저분해진다는 단점
    @Query(name = "Member.findByName")
    List<Member> findName(@Param("name") String name);

    // 메소드명 자동 매칭 cancel, 레포지토리에 직접 쿼리 입력
    @Query("select m from Member m where m.name = :name and m.age > :age")
    List<Member> findByNameAndAge(@Param("name") String name, @Param("age") int age);

}
