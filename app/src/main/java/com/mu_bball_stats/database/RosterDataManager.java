package com.mu_bball_stats.database;

import java.time.LocalDate;
import java.util.List;

import com.mu_bball_stats.model.Player;
import com.mu_bball_stats.model.PlayerStat;
import com.mu_bball_stats.model.Roster;
import com.mu_bball_stats.model.Session;

/**
 * Interface for DBTableManager class that manages player and player statistics data.
 */
public interface RosterDataManager {
    public Player getPlayer(int ID);
    public boolean addPlayer(Player player);
    public Roster getRoster();
    public void updatePlayer(int ID, String name, String position, int playerNumber, boolean isActive, int classYear);
    public boolean deletePlayer(int ID);
    public int addPlayerStats(int playerID, PlayerStat stat, Session session);
    public void updatePlayerStats(int playerID, LocalDate practiceDate, int drillNum, String statType, int attempted, int made);
    public List<Session> getPlayerStats(int playerID);
    public Session getSession(int sessionID);
    public List<Session> getSessions();
}
