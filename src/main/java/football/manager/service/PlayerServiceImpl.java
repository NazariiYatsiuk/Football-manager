package football.manager.service;

import football.manager.exception.DataProcessingException;
import football.manager.model.Player;
import football.manager.repository.PlayerRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(()
                -> new DataProcessingException("No player found by id " + id));
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        playerRepository.delete(findById(id));
    }

    @Override
    public BigDecimal getPrice(Player player) {
        return BigDecimal.valueOf(100000)
                .multiply(BigDecimal.valueOf(player.getExperienceInMonths()))
                .divide(BigDecimal.valueOf(player.getAge()), RoundingMode.FLOOR);
    }

    @Override
    public BigDecimal getPriceWithCommission(Player player) {
        return getPrice(player)
                .add(getPrice(player)
                        .multiply(player.getTeam().getCommissionPercent())
                        .divide(BigDecimal.valueOf(100)));
    }

    @Override
    public boolean isFreeAgent(Player player) {
        return player.getTeam() == null;
    }
}
