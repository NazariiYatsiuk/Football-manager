package football.manager.service;

import football.manager.exception.DataProcessingException;
import football.manager.model.Player;
import football.manager.model.Team;
import football.manager.repository.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
                .orElseThrow(() -> new DataProcessingException("No team found by id " + id));
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
    public void addPlayer(Team team, Player player) {
        player.setTeam(team);
        playerService.save(player);
    }

    @Override
    public void dismissPlayer(Team team, Player player) {
        if (team.getPlayers().contains(player)) {
            player.setTeam(null);
            playerService.save(player);
        }
    }

    @Override
    public void dismissAllPlayersFromTeam(Team team) {
        team
                .getPlayers()
                .forEach(p -> p.setTeam(null));
    }
}
