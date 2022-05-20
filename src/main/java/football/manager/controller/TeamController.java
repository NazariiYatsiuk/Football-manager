package football.manager.controller;

import football.manager.dto.request.TeamRequestDto;
import football.manager.dto.response.TeamResponseDto;
import football.manager.model.Team;
import football.manager.service.TeamService;
import football.manager.service.mapper.TeamMapper;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {
    private final TeamMapper teamMapper;
    private final TeamService teamService;

    @PostMapping
    public TeamResponseDto add(@RequestBody @Valid TeamRequestDto requestDto) {
        Team team = teamMapper.toModel(requestDto);
        team.setPlayers(Collections.emptyList());
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
        teamService.dismissAllPlayersFromTeam(teamService.findById(id));
        teamService.delete(id);
    }
}
