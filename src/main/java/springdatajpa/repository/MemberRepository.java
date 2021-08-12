package springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springdatajpa.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
