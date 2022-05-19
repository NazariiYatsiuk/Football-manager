package football.manager.service;

import football.manager.model.Player;
import football.manager.model.Team;
import football.manager.repository.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No team found by id " + id));
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        teamRepository.delete(findById(id));
    }

    @Override
    public void addPlayer(Long teamId, Long playerId) {
        Team team = findById(teamId);
        Player player = playerService.findById(playerId);
        team.getPlayers().add(player);
        player.setTeam(team);
        save(team);
        playerService.save(player);
    }

    @Override
    public void dismissPlayer(Long teamId, Long playerId) {
        Team team = findById(teamId);
        Player player = playerService.findById(playerId);
        team.getPlayers().remove(player);
        player.setTeam(null);
        save(team);
        playerService.save(player);
    }

    @Override
    public void dismissAllPlayersFromTeam(Long teamId) {
        findById(teamId)
                .getPlayers()
                .forEach(p -> p.setTeam(null));
    }
}
