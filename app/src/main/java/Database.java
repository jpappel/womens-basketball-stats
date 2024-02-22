import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
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
     * Creates a table named "Players" in the database if it does not already exist.
     * The table has the following columns:
     * - id: INTEGER (Primary Key)
     * - firstName: VARCHAR(20) (Not Null)
     * - lastName: VARCHAR(20) (Not Null)
     * - position: VARCHAR(30) (Not Null)
     * - seniority: CHAR(8) (Not Null)
     * - playerNum: INT (Not Null)
     *
     * @param conn the Connection object representing the database connection
     */
    public static void createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Players (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " firstName VARCHAR(20) NOT NULL,\n"
                + " lastName VARCHAR(20) NOT NULL,\n"
                + " position VARCHAR(30) NOT NULL,\n"
                + " seniority CHAR(8) NOT NULL,\n "
                + " playerNum INT NOT NULL\n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}