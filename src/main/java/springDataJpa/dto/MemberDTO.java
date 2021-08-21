package springDataJpa.dto;

import lombok.Data;

@Data
public class MemberDTO {

    String name;
    String teamName;

    public MemberDTO(String name, String teamName) {
        this.name = name;
        this.teamName = teamName;
    }
}
