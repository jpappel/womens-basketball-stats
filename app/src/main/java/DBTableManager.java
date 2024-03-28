import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                        //rs.getInt("playerActivity")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Adds a new player to the database.
     * @param player the Player object to add
     * @return true if the player was added successfully, false otherwise
     */
    @Override
    public boolean addPlayer(Player player) {
        String sql = "INSERT INTO Players(playerName, position, playerNum) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            //pstmt.setString(4, player.getSeniority());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the entire roster of players from the database.
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
                        //rs.getInt("playerActivity")
                );
                players.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Roster(players);
    }

    /**
     * Updates an existing player in the database.
     * @param ID the ID of the player to update
     * @param name the new name for the player
     * @param position the new position for the player
     * @param playerNumber the new player number
     */
    @Override
    public void updatePlayer(int ID, String name, String position, int playerNumber) {
        String sql = "UPDATE Players SET playerName = ?, position = ?, playerNum = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setInt(3, playerNumber);
            //pstmt.setString(4, playerActivity);
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
     * @param player the Player object to retrieve the ID for
     * @return the ID of the player if found, -1 otherwise
     */
    public int getPlayerID(Player player) {
        String sql = "SELECT id FROM Players WHERE playerName = ? AND position = ? AND playerNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            //pstmt.setString(4, player.getSeniority());
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

    @Override
    public boolean addPlayerStats(Player player) {
        String sql = "INSERT INTO PlayerStatistics (threePointsMade, threePointsAttempted, freeThrowsMade, freeThrowsAttempted) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, player.getThreePointersMade());
            pstmt.setInt(2, player.getThreePointersAttempted());
            pstmt.setInt(3, player.getFreeThrowsMade());
            pstmt.setInt(4, player.getFreeThrowAttempts());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void updatePlayerStats(int playerID, int threePointersMade, int threePointersAttempted, int freeThrowsMade, int freeThrowsAttempted) {
        String sql = "UPDATE PlayerStatistics SET threePointsMade = ?, threePointsAttempted = ?, freeThrowsMade = ?, freeThrowsAttempted = ? WHERE playerID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, threePointersMade);
            pstmt.setInt(2, threePointersAttempted);
            pstmt.setInt(3, freeThrowsMade);
            pstmt.setInt(4, freeThrowsAttempted);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Player stats updated successfully.");
            } else {
                System.out.println("Player with ID " + playerID + " not found.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the ID of a player from the database.
     * @param player the Player object to retrieve the ID for
     * @return the ID of the player if found, -1 otherwise
     */
    public int getPlayerID(Player player) {
        String sql = "SELECT id FROM Players WHERE playerName = ? AND position = ? AND playerNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            //pstmt.setString(4, player.getSeniority());
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

    @Override
    public Player getPlayerStats(int ID) {
        String sql = "SELECT * FROM PlayerStatistics WHERE playerID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Player(
                        rs.getInt("threePointsMade"),
                        rs.getInt("threePointsAttempted"),
                        rs.getInt("freeThrowsMade"),
                        rs.getInt("freeThrowsAttempted"),
                        rs.getDouble("threePointPercentage"),
                        rs.getDouble("freeThrowPercentage")
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

