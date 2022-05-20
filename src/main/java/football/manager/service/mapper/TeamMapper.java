package football.manager.service.mapper;

import football.manager.dto.request.TeamRequestDto;
import football.manager.dto.response.TeamResponseDto;
import football.manager.model.Player;
import football.manager.model.Team;
import football.manager.service.PlayerService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper implements RequestDtoMapper<TeamRequestDto, Team>,
        ResponseDtoMapper<Team, TeamResponseDto> {
    private final PlayerService playerService;

    public TeamMapper(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Team toModel(TeamRequestDto requestDto) {
        Team team = new Team();
        team.setTitle(requestDto.getTitle());
        team.setBalance(requestDto.getBalance());
        team.setCommissionPercent(requestDto.getCommissionPercent());
        return team;
    }

    @Override
    public TeamResponseDto toDto(Team team) {
        TeamResponseDto responseDto = new TeamResponseDto();
        responseDto.setId(team.getId());
        responseDto.setTitle(team.getTitle());
        responseDto.setBalance(team.getBalance());
        responseDto.setCommissionPercent(team.getCommissionPercent());
        responseDto.setPlayerIds(team.getPlayers().stream()
                .map(Player::getId)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
