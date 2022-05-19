package football.manager.service;

import football.manager.model.Team;
import java.util.List;

public interface TeamService {
    Team save(Team team);

    Team findById(Long id);

    List<Team> findAll();

    void delete(Long id);

    void addPlayer(Long teamId, Long playerId);

    void dismissPlayer(Long teamId, Long playerId);

    void dismissAllPlayersFromTeam(Long teamId);
}
