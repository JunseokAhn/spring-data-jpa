package springDataJpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springDataJpa.domain.Member;
import springDataJpa.repository.MemberRepository;

import javax.annotation.PostConstruct;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getName();
    }

    // memberList?page=3&size=5  ...
    @GetMapping("/memberList")
    public Page<Member> findMemberList(Pageable pageable) {
        Page<Member> memberList = memberRepository.findAll(pageable);
        return memberList;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++)
            memberRepository.save(new Member("member"));
    }
}
