package com.mu_bball_stats.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.sql.*;
import java.time.LocalDate;

import com.mu_bball_stats.model.Player;
import com.mu_bball_stats.model.PlayerStat;
import com.mu_bball_stats.model.Roster;
import com.mu_bball_stats.model.Session;

/**
 * Implements the RosterDataManager interface and provides methods
 * for managing player data stored in the Players database table.
 */
public class DBTableManager implements RosterDataManager {

    private final Connection conn;

    public DBTableManager(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retrieves a player from the database based on the specified ID.
     * @author Alan
     * @param ID the ID of the player to retrieve
     * @return the Player object if found, null otherwise
     */
    @Override
    public Player getPlayer(int ID) {
        String sql = "SELECT * FROM Players WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Player(
                        rs.getString("playerName"),
                        rs.getString("position"),
                        rs.getInt("playerNum")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new player to the database.
     * @author Alan, J.P.
     * @param player the Player object to add
     * @return true if the player was added successfully, false otherwise
     */
    @Override
    public boolean addPlayer(Player player) {
        String sql = "INSERT INTO Players(playerName, position, playerNum, playerActivity) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            pstmt.setInt(4, player.isPlaying() ? 1 : 0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the entire roster of players from the database.
     * @author Alan, J.P.
     * @return the Roster object containing all players
     */
    @Override
    public Roster getRoster() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM Players";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Player player = new Player(
                        rs.getString("playerName"),
                        rs.getString("position"),
                        rs.getInt("playerNum")
                );
                player.setID(rs.getInt("id"));
                player.setPlaying(rs.getInt("playerActivity") == 1);
                TreeMap<Integer, PlayerStat> stats = getPlayerStats(player.getID());
                if(stats != null){
                    player.setStat(stats);
                }
                players.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Roster(players);
    }

    /**
     * Updates a player in the database based on the specified ID.
     * @author Alan, J.P.
     * @param ID the ID of the player to update
     * @param name the new name of the player
     * @param position the new position of the player
     * @param playerNumber the new player number
     * @param isActive the new activity status of the player
     */
    @Override
    public void updatePlayer(int ID, String name, String position, int playerNumber, boolean isActive) {
        String sql = "UPDATE Players SET playerName = ?, position = ?, playerNum = ?, playerActivity = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setInt(3, playerNumber);
            pstmt.setInt(4, isActive ? 1 : 0);
            pstmt.setInt(5, ID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Player updated successfully.");
            } else {
                System.out.println("Player with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a player from the database based on the specified ID.
     * @author Alan, J.P.
     * @param ID the ID of the player to delete
     * @return true if the player was deleted successfully, false otherwise
     */
    @Override
    public boolean deletePlayer(int ID) {
        String sql = "DELETE FROM Players WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Player removed successfully.");
            } else {
                System.out.println("Player with ID " + ID + " not found.");
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the ID of a player from the database.
     * @author Alan, J.P.
     * @param player the Player object to retrieve the ID for
     * @return the ID of the player if found, -1 otherwise
     */
    public int getPlayerID(Player player) {
        String sql = "SELECT id FROM Players WHERE playerName = ? AND position = ? AND playerNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Adds player statistics to the database.
     * @author J.P.
     * @param playerID the ID of the player
     * @param stat the PlayerStat object containing the statistics to be added
     * @param session the session which the statistics are associated with
     * @return the ID of the added statistics, or -1 if an error occurred
     */
    @Override
    public int addPlayerStats(int playerID, PlayerStat stat, Session session) {
        String sql = "INSERT INTO PlayerStatistics (playerID, practiceDate, drillNum, statType, attempted, made) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerID);
            pstmt.setDate(2, Date.valueOf(session.getDate())); // Convert LocalDate to SQL Date
            pstmt.setInt(3, session.getID());
            pstmt.setString(4, stat.getStatType());
            pstmt.setInt(5, stat.getAttempted());
            pstmt.setInt(6, stat.getMade());
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int statsId = generatedKeys.next() ? generatedKeys.getInt(1) : -1;
            return statsId;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Updates player statistics in the database.
     * @author J.P.
     * @param playerID the ID of the player
     * @param practiceDate the date of the practice
     * @param drillNum the number of the drill
     * @param statType the type of statistic being recorded (e.g. "freeThrow", "threePoint")
     * @param attempted the number of attempts
     * @param made the number of successful attempts
     */
    @Override
    public void updatePlayerStats(int playerID, LocalDate practiceDate, int drillNum, String statType, int attempted,
            int made) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePlayerStats'");
    }

    /**
     * Returns all the stats for a player.
     * @author J.P.
     * @param playerID the ID of the player
     * @return all the stats for a player
     */
    @Override
    public TreeMap<Integer, PlayerStat> getPlayerStats(int playerID) {
        String sql = "SELECT * FROM PlayerStatistics WHERE playerID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerID);
            ResultSet rs = pstmt.executeQuery();
            TreeMap<Integer, PlayerStat> playerStats = new TreeMap<>();
            while (rs.next()) {
                PlayerStat stat = new PlayerStat();
                stat.setPracticeDate(rs.getDate("practiceDate"));
                stat.setDrillNum(rs.getInt("drillNum"));
                stat.setStatType(rs.getString("statType"));
                stat.setAttempted(rs.getInt("attempted"));
                stat.setMade(rs.getInt("made"));
                playerStats.put(rs.getInt("id"), stat);
            }
            return playerStats;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Session getSession(int sessionID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSession'");
    }

}
