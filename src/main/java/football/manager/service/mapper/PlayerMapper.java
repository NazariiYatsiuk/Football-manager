package football.manager.service.mapper;

import football.manager.dto.request.PlayerRequestDto;
import football.manager.dto.response.PlayerResponseDto;
import football.manager.model.Player;
import football.manager.model.Team;
import football.manager.service.TeamService;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper implements RequestDtoMapper<PlayerRequestDto, Player>,
        ResponseDtoMapper<Player, PlayerResponseDto> {
    private final TeamService teamService;

    public PlayerMapper(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public Player toModel(PlayerRequestDto requestDto) {
        Player player = new Player();
        Team team = requestDto.getTeamId() == null
                ? null : teamService.findById(requestDto.getTeamId());
        player.setName(requestDto.getName());
        player.setSecondName(requestDto.getSecondName());
        player.setAge(requestDto.getAge());
        player.setExperienceInMonths(requestDto.getExperienceInMonths());
        player.setTeam(team);
        return player;
    }

    @Override
    public PlayerResponseDto toDto(Player player) {
        PlayerResponseDto responseDto = new PlayerResponseDto();
        String team = player.getTeam() == null
                ? null : player.getTeam().getTitle();
        responseDto.setId(player.getId());
        responseDto.setName(player.getName());
        responseDto.setSecondName(player.getSecondName());
        responseDto.setAge(player.getAge());
        responseDto.setExperienceInMonths(player.getExperienceInMonths());
        responseDto.setTeam(team);
        return responseDto;
    }
}
