package football.manager.controller;

import football.manager.dto.request.PlayerRequestDto;
import football.manager.dto.response.PlayerResponseDto;
import football.manager.model.Player;
import football.manager.service.PlayerService;
import football.manager.service.mapper.PlayerMapper;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerMapper playerMapper;
    private final PlayerService playerService;

    @PostMapping
    public PlayerResponseDto add(@RequestBody @Valid PlayerRequestDto requestDto) {
        Player player = playerMapper.toModel(requestDto);
        return playerMapper.toDto(playerService.save(player));
    }

    @GetMapping("/{id}")
    public PlayerResponseDto findById(@PathVariable Long id) {
        return playerMapper.toDto(playerService.findById(id));
    }

    @GetMapping
    public List<PlayerResponseDto> findAll() {
        return playerService.findAll()
                .stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    public PlayerResponseDto update(@PathVariable Long id,
                                    @RequestBody @Valid PlayerRequestDto requestDto) {
        Player player = playerService.findById(id);
        player.setAge(requestDto.getAge());
        player.setExperienceInMonths(requestDto.getExperienceInMonths());
        return playerMapper.toDto(playerService.save(player));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        playerService.delete(id);
    }
}
