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

@RequiredArgsConstructor
@RestController
@RequestMapping("/transfer")
public class TransferController {
    private final TransferService transferService;
    private final PlayerService playerService;
    private final TeamService teamService;

    @PatchMapping
    public String transfer(@RequestParam("buyer-team-id") Long buyerTeamId,
                           @RequestParam("player-id") Long playerId) {
        Player transferPlayer = playerService.findById(playerId);
        Team previousTeam = transferPlayer.getTeam();
        if (transferPlayer.getTeam().equals(teamService.findById(buyerTeamId))
                || playerService.isFreeAgent(transferPlayer)) {
            throw new RuntimeException("Player " + transferPlayer.getName() + " "
                    + transferPlayer.getSecondName()
                    + " can't be transferred, because he is a free agent "
                    + "or already has existing contract with "
                    + teamService.findById(buyerTeamId).getTitle());
        }
        transferService.transfer(buyerTeamId, playerId);
        return "Player " + transferPlayer.getName() + " "
                + transferPlayer.getSecondName()
                + " was successfully transferred from "
                + previousTeam.getTitle() + " to "
                + teamService.findById(buyerTeamId).getTitle();
    }
}
