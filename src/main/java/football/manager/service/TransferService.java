package football.manager.service;

import football.manager.model.Player;
import football.manager.model.Team;

public interface TransferService {
    void transfer(Team team, Player player);

    void signFreeAgent(Team team, Player player);

    void firePlayer(Team team, Player player);
}
