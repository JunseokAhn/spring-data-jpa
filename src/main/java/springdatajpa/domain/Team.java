package springDataJpa.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {

    @Id
    @GeneratedValue
    @JoinColumn(name = "member_id")
    Long id;
    String name;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    List<Member> memberList = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
