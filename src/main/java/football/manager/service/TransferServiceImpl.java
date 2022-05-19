package football.manager.service;

import football.manager.model.Player;
import football.manager.model.Team;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    private final PlayerService playerService;
    private final TeamService teamService;

    @Override
    public void transfer(Long buyerTeamId, Long playerId) {
        Player player = playerService.findById(playerId);
        Team buyer = teamService.findById(buyerTeamId);
        Team seller = player.getTeam();
        BigDecimal transferPrice = playerService.getPriceWithCommission(player);
        if (transferPrice.compareTo(buyer.getBalance()) > 0) {
            throw new RuntimeException("Buyer has not enough money to make this transfer");
        }
        BigDecimal newBuyerBalance = buyer.getBalance().subtract(transferPrice);
        buyer.setBalance(newBuyerBalance);
        BigDecimal newSellerBalance = seller.getBalance().add(transferPrice);
        seller.setBalance(newSellerBalance);
        teamService.dismissPlayer(seller.getId(), playerId);
        teamService.addPlayer(buyerTeamId, playerId);
        teamService.save(buyer);
        teamService.save(seller);
    }
}
