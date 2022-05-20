package football.manager.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerResponseDto {
    private Long id;
    private String name;
    private String secondName;
    private int age;
    private int experienceInMonths;
    private String team;
}
