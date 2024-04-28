package com.mu_bball_stats.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the database connection and the creation of database tables.
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:WomenBBall.db";

    /**
     * Establishes a connection to the database.
     * @author Alan
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
     * @author Alan
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
     * - playerActivity: INT (Default 1 (True))
     * - classYear: INT (Not Null)
     * @author Alan
     * @author J.P.
     * @author Megan
     * @param conn the Connection object representing the database connection
     */
    public static void createPlayersTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Players (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " playerName VARCHAR(40) NOT NULL,\n"
                + " position VARCHAR(30) NOT NULL,\n"
                + " playerNum INT NOT NULL,\n"
                + " playerActivity INT NOT NULL,\n"
                + " classYear INT NOT NULL\n"
                + ");";
        executeStatement(conn, sql, "Players table created successfully.");
    }


    /**
     * Creates a table named "PlayerStatistics" in the database if it does not already exist.
     * The table has the following columns:
     * - id: INTEGER (Primary Key)
     * - playerID: INTEGER (Foreign Key)
     * - practiceDate: DATE
     * - drillNum: INTEGER
     * - statType: TEXT (Check: statType IN ('freeThrow','threePoint') Default 'freeThrow')
     * - attempted: INTEGER
     * - made: INTEGER
     * - statPercentage: REAL (Generated Always As (Made / Attempted))
     * @author Alan
     * @author JP
     * @param conn the Connection object representing the database connection
     */
    public static void createPlayerStatsTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS PlayerStatistics (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " playerID INTEGER,\n" +
                " sessionID INTEGER,\n" +
                " statType TEXT CHECK( statType IN ('freeThrow','threePoint') ) NOT NULL DEFAULT 'freeThrow',\n" +
                " attempted INTEGER,\n" +
                " made INTEGER,\n" +
                " statPercentage REAL GENERATED ALWAYS AS (Made / Attempted),\n" +
                " FOREIGN KEY (playerID) REFERENCES Players(id) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                " FOREIGN KEY (sessionID) REFERENCES Sessions(id) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ");";
        executeStatement(conn, sql, "Player stats table created successfully.");
    }

    /**
     * Creates a table named "Sessions" in the database if it does not already exist.
     * The table has the following columns:
     * - id: INTEGER (Primary Key)
     * - sessionDate: DATE
     * - drillNum: INTEGER
     * 
     * @author JP
     * @param conn the Connection object representing the database connection
     */
    public static void createSessionTable(Connection conn){
        String sql = "CREATE TABLE IF NOT EXISTS Sessions (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " sessionDate DATE,\n" +
                " drillNum INTEGER,\n" +
                " UNIQUE(sessionDate, drillNum)\n" +
                ");";
        executeStatement(conn, sql, "Sessions table created successfully.");
    }

    }





