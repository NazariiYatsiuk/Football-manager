package football.manager.service;

import football.manager.model.Player;
import java.math.BigDecimal;
import java.util.List;

public interface PlayerService {
    Player save(Player player);

    Player findById(Long id);

    List<Player> findAll();

    void delete(Long id);

    BigDecimal getPrice(Player player);

    BigDecimal getPriceWithCommission(Player player);

    boolean isFreeAgent(Player player);
}
