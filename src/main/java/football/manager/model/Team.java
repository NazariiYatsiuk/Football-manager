package football.manager.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BigDecimal balance;
    private BigDecimal commissionPercent;
    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        return commissionPercent == team.commissionPercent
                && Objects.equals(id, team.id)
                && Objects.equals(title, team.title)
                && Objects.equals(balance, team.balance)
                && Objects.equals(players, team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, balance, commissionPercent, players);
    }
}
