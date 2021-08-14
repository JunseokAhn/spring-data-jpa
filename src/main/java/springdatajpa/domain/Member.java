package springdatajpa.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Member {

    @Id
    @GeneratedValue
    @JoinColumn(name = "member_id")
    Long id;
    String name;
    int age;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.memberList.add(this);
    }
}
