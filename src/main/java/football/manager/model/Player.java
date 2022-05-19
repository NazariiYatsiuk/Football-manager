package football.manager.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String secondName;
    @Min(value = 14)
    private int age;
    @PositiveOrZero
    private int experienceInMonths;
    @ManyToOne
    private Team team;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return age == player.age
                && experienceInMonths == player.experienceInMonths
                && Objects.equals(id, player.id)
                && Objects.equals(name, player.name)
                && Objects.equals(secondName, player.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, age, experienceInMonths);
    }
}
