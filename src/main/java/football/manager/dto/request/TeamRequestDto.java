package football.manager.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class TeamRequestDto {
    @NotBlank
    private String title;
    @PositiveOrZero
    private BigDecimal balance;
    @Min(value = 0)
    @Max(value = 10)
    private BigDecimal commissionPercent;
}
