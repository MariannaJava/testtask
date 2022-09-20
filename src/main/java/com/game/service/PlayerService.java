package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import java.util.List;

public interface PlayerService {
    public List<Player> getAllPlayers(String name, String title, String race, String profession,
                                      Long after, Long before, Boolean banned,
                                      Integer minExperience, Integer maxExperience,
                                      Integer minLevel, Integer maxLevel,
                                      PlayerOrder order,
                                      Integer pageNumber, Integer pageSize);

    public List<Player> getAllPlayers(String name, String title, String race, String profession,
                                      Long after, Long before, Boolean banned,
                                      Integer minExperience, Integer maxExperience,
                                      Integer minLevel, Integer maxLevel);
    public Player getPlayer(String id);

    public void savePlayer(Player player);

    public boolean addPlayer(Player player);

    public Player updatePlayer(String id, Player player);
    public void deletePlayer(String id);

}
