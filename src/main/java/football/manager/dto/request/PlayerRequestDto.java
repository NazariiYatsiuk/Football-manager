package football.manager.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PlayerRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String secondName;
    @Min(value = 14)
    private int age;
    @Positive
    private int experienceInMonths;
    private Long teamId;
}
