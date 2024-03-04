import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBTableManager implements RosterDataManager {

    private final Connection conn;

    public DBTableManager(Connection conn) {
        this.conn = conn;
    }


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
                        //rs.getInt("id"),
                        //rs.getString("seniority")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void addPlayer(Player player) {
        String sql = "INSERT INTO Players(playerName, position, playerNum) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getPosition());
            pstmt.setInt(3, player.getNumber());
            //pstmt.setString(4, player.getSeniority());
            pstmt.executeUpdate();
            System.out.println("Player added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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
                        //rs.getInt("id"),
                        //rs.getString("seniority")
                );
                players.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Roster(players);
    }

    @Override
    public void updatePlayer(int ID, String name, String position, int playerNumber) {
        String sql = "UPDATE Players SET playerName = ?, position = ?, playerNum = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setInt(3, playerNumber);
            //pstmt.setString(4, seniority);
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

    @Override
    public void deletePlayer(int ID) {
        String sql = "DELETE FROM Players WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Player removed successfully.");
            } else {
                System.out.println("Player with ID " + ID + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
