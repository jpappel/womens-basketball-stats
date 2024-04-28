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
import java.util.HashMap;

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
                        rs.getInt("playerNum"),
                        rs.getInt("classYear")
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
        String sql = "INSERT INTO Players(playerName, position, playerNum, playerActivity, classYear) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            pstmt.setInt(4, player.isPlaying() ? 1 : 0);
            pstmt.setInt(5, player.getClassYear());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the entire roster of players from the database.
     * @author Alan
     * @author JP
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
                        rs.getInt("playerNum"),
                        rs.getInt("classYear")
                );
                player.setID(rs.getInt("id"));
                player.setPlaying(rs.getInt("playerActivity") == 1);
                players.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Roster(players);
    }

    /**
     * Updates a player in the database based on the specified ID.
     * @author Alan
     * @author JP
     * @param ID the ID of the player to update
     * @param name the new name of the player
     * @param position the new position of the player
     * @param playerNumber the new player number
     * @param isActive the new activity status of the player
     * @param classYear the new class year of the player
     */
    @Override
    public void updatePlayer(int ID, String name, String position, int playerNumber, boolean isActive, int classYear) {
        String sql = "UPDATE Players SET playerName = ?, position = ?, playerNum = ?, playerActivity = ?, classYear = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setInt(3, playerNumber);
            pstmt.setInt(4, isActive ? 1 : 0);
            pstmt.setInt(5, classYear);
            pstmt.setInt(6, ID);
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
     * @author Alan
     * @author JP
     * @param player the Player object to retrieve the ID for
     * @return the ID of the player if found, -1 otherwise
     */
    public int getPlayerID(Player player) {
        String sql = "SELECT id FROM Players WHERE playerName = ? AND position = ? AND playerNum = ? AND classYear = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            pstmt.setInt(4, player.getClassYear());
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
     * @author JP
     * @param playerID the ID of the player
     * @param stat the PlayerStat object containing the statistics to be added
     * @param session the session which the statistics are associated with
     * @return the ID of the added statistics, or -1 if an error occurred
     */
    @Override
    public int addPlayerStats(int playerID, PlayerStat stat, Session session) {
        // if session does not exist, create it
        if (session.getID() == -1) {
            int sessionID = createSession(session.getDate());
            session.setID(sessionID);
        }
        String sql = "INSERT INTO PlayerStatistics (playerID, sessionID, statType, attempted, made) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerID);
            pstmt.setInt(2, session.getID());
            pstmt.setString(3, stat.getStatType());
            pstmt.setInt(4, stat.getAttempted());
            pstmt.setInt(5, stat.getMade());
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
     * Creates a new session in the database with the specified date and drill number.
     *
     * @author JP
     * @param date The date of the session.
     * @return The ID of the created session, or -1 if an error occurred.
     */
    public int createSession(LocalDate date) {
        String sql = "INSERT INTO Sessions (sessionDate) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int sessionID = generatedKeys.next() ? generatedKeys.getInt(1) : -1;
            return sessionID;
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
     * @author JP
     * @param playerID the ID of the player
     * @return a list of sessions with stats for the player if found, else null
     */
    @Override
    public List<Session> getPlayerStats(int playerID) {
        List<Session> sessions = new ArrayList<>();
        Player player = getPlayer(playerID);
        if (player == null){
            return null;
        }
        
        String sql = "SELECT * FROM PlayerStatistics WHERE playerID = ?";

        // get the player stats from the database
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Session session = getSession(rs.getInt("sessionID"));
                if(session == null) return null;
                HashMap<Player, ArrayList<PlayerStat>> stats = new HashMap<>();
                stats.put(player, session.getPlayerStats().get(player));
                sessions.add(new Session(session.getDate(),
                        session.getID(), session.getDrillNum(), stats));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        return sessions;
    }

    /**
     * Represents a session in the basketball statistics database.
     * A session contains information such as session date, session ID, and drill number.
     * @author JP
     * @param sessionID the ID of the session
     * @return a session object with stats for each player if found, else null
     */
    @Override
    public Session getSession(int sessionID) {
        Session session = null;
        String sql = "SELECT * FROM Sessions WHERE id = ?";

        // get the session from the database
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                session = new Session(rs.getDate("sessionDate").toLocalDate(),
                        rs.getInt("id"),
                        rs.getInt("drillNum"));
            }
            else {
                return session;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return session;
        }

        //get players that have stats for the session
        ArrayList<Player> players = new ArrayList<>();
        sql = "SELECT * FROM PlayerStatistics WHERE sessionID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                players.add(getPlayer(rs.getInt("playerID")));
                Player player = players.getLast();
                System.out.println(player.getName());
                if(player == null) return null;

                PlayerStat stat = new PlayerStat(rs.getString("statType"),
                        rs.getInt("made"),
                        rs.getInt("attempted"));

                session.addStat(players.getLast(), stat);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return session;
        }


        return session;
    }

    /**
     * Retrieves all sessions from the database.
     * @return a list of all sessions if found, else null
     */
    @Override
    public List<Session> getSessions() {
        List<Integer> sessionIds = new ArrayList<>();
        String sql = "SELECT id FROM Sessions";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                sessionIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        List<Session> sessions = new ArrayList<>();
        for (int id : sessionIds) {
            Session session = getSession(id);
            if (session == null) return null;
            sessions.add(session);
        }

        return sessions;
    }

}
