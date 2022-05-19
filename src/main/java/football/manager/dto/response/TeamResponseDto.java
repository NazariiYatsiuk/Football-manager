package football.manager.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto {
    private Long id;
    private String title;
    private BigDecimal balance;
    private BigDecimal commissionPercent;
    private List<Long> playerIds;
}
