import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:WomenBBall.db";

    /**
     * Establishes a connection to the database.
     *
     * @return the Connection object representing the database connection, or null if an error occurs.
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Executes the provided SQL statement using the given database connection,
     * and prints a success message upon successful execution.
     *
     * @param conn           the database connection
     * @param sql            the SQL statement to execute
     * @param successMessage the message to print upon successful execution
     */
    private static void executeStatement(Connection conn, String sql, String successMessage) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println(successMessage);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a table named "Players" in the database if it does not already exist.
     * The table has the following columns:
     * - id: INTEGER (Primary Key)
     * - playerName: VARCHAR(40) (Not Null)
     * - position: VARCHAR(30) (Not Null)
     * - playerNum: INT (Not Null)
     * - playerActivity: BOOLEAN (Default 0)
     *
     * @param conn the Connection object representing the database connection
     */
    public static void createPlayersTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Players (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " playerName VARCHAR(40) NOT NULL,\n"
                + " position VARCHAR(30) NOT NULL,\n"
                + " playerNum INT NOT NULL,\n"
                + " isPlaying INT NOT NULL\n"
                + ");";
        executeStatement(conn, sql, "Players table created successfully.");
    }

    /**
     * Creates a table named "Players" in the database if it does not already exist.
     *
     * @param conn the Connection object representing the database connection
     */
    public static void createPlayerStatsTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS PlayerStatistics (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " playerID INTEGER,\n" +
                " freeThrowsAttempted INTEGER,\n" +
                " threePointsAttempted INTEGER,\n" +
                " freeThrowsMade INTEGER,\n" +
                " threePointsMade INTEGER,\n" +
                " freeThrowPercentage REAL GENERATED ALWAYS AS (freeThrowsMade / freeThrowsAttempted),\n" +
                " threePointPercentage REAL GENERATED ALWAYS AS (threePointsMade / threePointsAttempted),\n" +
                " FOREIGN KEY (playerID) REFERENCES Players(id) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ");";
        executeStatement(conn, sql, "Player stats table created successfully.");
    }
}





