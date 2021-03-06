package football.manager.service;

import football.manager.model.Player;
import football.manager.model.Team;
import java.util.List;

public interface TeamService {
    Team save(Team team);

    Team findById(Long id);

    List<Team> findAll();

    void delete(Long id);

    void addPlayer(Team team, Player player);

    void dismissPlayer(Team team, Player player);

    void dismissAllPlayersFromTeam(Team team);
}
