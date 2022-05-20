package football.manager.service;

import football.manager.exception.TransferException;
import football.manager.model.Player;
import football.manager.model.Team;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final PlayerService playerService;
    private final TeamService teamService;

    @Override
    public void transfer(Team buyer, Player player) {
        if (playerService.isFreeAgent(player)) {
            throw new TransferException("Player " + player.getName() + " "
                    + player.getSecondName()
                    + " can't be transferred, because he is a free agent");
        }
        if (player.getTeam().equals(buyer)) {
            throw new TransferException("You cannot transfer "
                    + "a player to the team he is already in");
        }
        Team seller = player.getTeam();
        BigDecimal transferPrice = playerService.getPriceWithCommission(player);
        if (transferPrice.compareTo(buyer.getBalance()) > 0) {
            throw new TransferException("Buyer has not enough money to make this transfer");
        }
        buyer.setBalance(buyer.getBalance().subtract(transferPrice));
        seller.setBalance(seller.getBalance().add(transferPrice));
        teamService.dismissPlayer(seller, player);
        teamService.addPlayer(buyer, player);
        teamService.save(buyer);
        teamService.save(seller);
    }

    @Override
    public void signFreeAgent(Team team, Player player) {
        if (!playerService.isFreeAgent(player)) {
            throw new TransferException("Player " + player.getName() + " "
                    + player.getSecondName()
                    + " is not a free agent");
        }
        teamService.addPlayer(team, player);
    }

    @Override
    public void firePlayer(Team team, Player player) {
        if (!team.getPlayers().contains(player)) {
            throw new TransferException("Player " + player.getName() + " "
                    + player.getSecondName()
                    + " does not have a contract with " + team.getTitle());
        }
        player.setTeam(null);
        playerService.save(player);
    }
}
