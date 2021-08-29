package springDataJpa.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id", "name"})
public class Team implements Persistable<String> {

    @Id
    @Column(name = "team_id")
    String id;
    String name;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    List<Member> memberList = new ArrayList<>();

    @CreatedDate
    LocalDateTime createdDate;

    public Team(String name) {

        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    // em.persist를 시행하면 작동되는 isNew는 id가 null이거나 0이면
    // 새로운 엔티티라고 판단하기때문에,
    // id값이 String으로 주어질때, Persistable을 상속받아
    // 해당 메소드를 재정의 해줘야한다
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
