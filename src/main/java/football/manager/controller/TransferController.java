package football.manager.controller;

import football.manager.model.Player;
import football.manager.model.Team;
import football.manager.service.PlayerService;
import football.manager.service.TeamService;
import football.manager.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;
    private final PlayerService playerService;
    private final TeamService teamService;

    @PatchMapping("/transfer-a-player")
    public String transfer(@RequestParam("buyer-team-id") Long buyerId,
                           @RequestParam("player-id") Long playerId) {
        Player transferPlayer = playerService.findById(playerId);
        Team buyer = teamService.findById(buyerId);
        Team seller = transferPlayer.getTeam();
        transferService.transfer(buyer, transferPlayer);
        return "Player " + transferPlayer.getName() + " "
                + transferPlayer.getSecondName()
                + " was successfully transferred from "
                + seller + " to "
                + buyer.getTitle();
    }

    @PatchMapping("/sign-free-agent")
    public String signFreeAgent(@RequestParam(value = "team-id") Long teamId,
                                @RequestParam(value = "player-id") Long playerId) {
        Player player = playerService.findById(playerId);
        Team team = teamService.findById(teamId);
        transferService.signFreeAgent(team, player);
        return player.getName() + " " + player.getSecondName()
                + " was successfully signed by " + team.getTitle();
    }

    @PatchMapping("/fire-player")
    public String dismissPlayer(@RequestParam(value = "team-id") Long teamId,
                                @RequestParam(value = "player-id") Long playerId) {
        Player player = playerService.findById(playerId);
        Team team = teamService.findById(teamId);
        transferService.firePlayer(team, player);
        return player.getName() + " " + player.getSecondName()
                + " was successfully dismissed from " + team.getTitle();
    }
}
