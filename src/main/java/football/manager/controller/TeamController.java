package football.manager.controller;

import football.manager.dto.request.TeamRequestDto;
import football.manager.dto.response.TeamResponseDto;
import football.manager.model.Player;
import football.manager.model.Team;
import football.manager.service.PlayerService;
import football.manager.service.TeamService;
import football.manager.service.mapper.TeamMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamMapper teamMapper;
    private final TeamService teamService;
    private final PlayerService playerService;

    @PostMapping
    public TeamResponseDto add(@RequestBody @Valid TeamRequestDto requestDto) {
        Team team = teamMapper.toModel(requestDto);
        return teamMapper.toDto(teamService.save(team));
    }

    @GetMapping("/{id}")
    public TeamResponseDto findById(@PathVariable Long id) {
        return teamMapper.toDto(teamService.findById(id));
    }

    @GetMapping
    public List<TeamResponseDto> findAll() {
        return teamService.findAll()
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    public TeamResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid TeamRequestDto requestDto) {
        Team team = teamService.findById(id);
        team.setTitle(requestDto.getTitle());
        team.setBalance(requestDto.getBalance());
        team.setCommissionPercent(requestDto.getCommissionPercent());
        return teamMapper.toDto(teamService.save(team));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teamService.dismissAllPlayersFromTeam(id);
        teamService.delete(id);
    }

    @PatchMapping("/sign-free-agent")
    public String signFreeAgent(@RequestParam(value = "team-id") Long teamId,
                                @RequestParam(value = "player-id") Long playerId) {
        Player player = playerService.findById(playerId);
        Team team = teamService.findById(teamId);
        if (!playerService.isFreeAgent(player)) {
            throw new RuntimeException("Player " + player.getName()
                    + " "
                    + player.getSecondName()
                    + " already has an existing contract with " + player.getTeam().getTitle());
        }
        teamService.addPlayer(teamId, playerId);
        return player.getName() + " " + player.getSecondName()
                + " was successfully signed by " + team.getTitle();
    }

    @PatchMapping("/dismiss-player")
    public String dismissPlayer(@RequestParam(value = "team-id") Long teamId,
                                @RequestParam(value = "player-id") Long playerId) {
        Player player = playerService.findById(playerId);
        Team team = teamService.findById(teamId);
        if (!team.getPlayers().contains(player)) {
            throw new RuntimeException("Player " + player.getName()
                    + " "
                    + player.getSecondName()
                    + " does not have a contract with " + team.getTitle());
        }
        teamService.dismissPlayer(teamId, playerId);
        return player.getName() + " " + player.getSecondName()
                + " was successfully dismissed from " + teamService.findById(teamId).getTitle();
    }
}
