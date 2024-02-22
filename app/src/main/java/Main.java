import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;

import io.javalin.Javalin;

public class Main {
    private static final String RESOURCE_ROOT = "src/main/resources/public";

    public static void main(String[] args) {

        // Connect to the SQLite database
        try (Connection conn = Database.connect()) {
            if (conn != null) {
                System.out.println("Connected to the database.");

                // Creates Players table if it doesn't exist
                Database.createTable(conn);

            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        })
                .get("/add-player", ctx -> {
                    byte[] htmlContent = Files.readAllBytes(Paths.get(RESOURCE_ROOT, "add_player.html"));
                    ctx.contentType("text/html");
                    ctx.result(htmlContent);
                })
                .get("/roster", ctx -> {
                    byte[] htmlContent = Files.readAllBytes(Paths.get(RESOURCE_ROOT, "roster.html"));
                    ctx.contentType("text/html");
                    ctx.result(htmlContent);
                })
                .post("/add-player", ctx -> {
                    Player player = ctx.bodyAsClass(Player.class);
                    ctx.contentType("application/json");
                    ctx.result(player.toJson());
                })
                .start(7070);

    }
}
