package com.mu_bball_stats.database;

import java.util.TreeMap;

/**
 * Interface for DBTableManager class that manages player and player statistics data.
 */
public interface RosterDataManager {
    public Player getPlayer(int ID);
    public boolean addPlayer(Player player);
    public Roster getRoster();
    public void updatePlayer(int ID, String name, String position, int playerNumber, boolean isActive);
    public boolean deletePlayer(int ID);
    public int addPlayerStats(int playerID, PlayerStat stat);
    public void updatePlayerStats(int playerID, int threePointersMade, int threePointersAttempted, int freeThrowsMade, int freeThrowsAttempted);
    public TreeMap<Integer, PlayerStat> getPlayerStats(int playerID);
}
