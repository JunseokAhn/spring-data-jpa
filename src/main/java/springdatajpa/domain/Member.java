package springDataJpa.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
@NamedQuery(
        name = "Member.findByName",
        query = "select m from Member m where m.name = :name"
)
public class Member {

    @Id
    @GeneratedValue
    @JoinColumn(name = "member_id")
    Long id;
    String name;
    int age;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.memberList.add(this);
    }
}
