package springdatajpa.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue
    Long id;
    String name;

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }
}
