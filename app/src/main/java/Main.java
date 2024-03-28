import java.nio.file.Files;
import java.nio.file.Paths;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import com.google.gson.Gson;

import java.sql.Connection;

import javax.xml.crypto.Data;

public class Main {
    private static final String RESOURCE_ROOT = "src/main/resources/public";

    public static void main(String[] args) {

        //attempts to make database connection
        Connection conn = DatabaseManager.connect();
        if(conn == null){
            System.err.println("Failed to connect to the database.");
            return;
        }

        //creates players table if it does not exist
        DatabaseManager.createPlayersTable(conn);
        // creates player stats table if it does not exist
        DatabaseManager.createPlayerStatsTable(conn);

        final DBTableManager dbTableManager = new DBTableManager(conn);

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinJte());
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
                .get("/api/roster", ctx -> {
                    Roster roster = dbTableManager.getRoster();
                    ctx.contentType("application/json");
                    ctx.result(roster.toJson());
                })
                .post("/add-player", ctx -> {
                    Player player = ctx.bodyAsClass(Player.class);
                    ctx.contentType("application/json");
                    boolean addedPlayer = dbTableManager.addPlayer(player);
                    ctx.result("{\"success\": " + addedPlayer + "}");
                })

                .delete("/players/{name}", ctx -> {
                    String name = ctx.pathParam("name");
                    ctx.contentType("application/json");

                    //TODO: refactor
                    Roster roster = dbTableManager.getRoster();
                    Player player = roster.getPlayerByName(name);
                    int id = dbTableManager.getPlayerID(player);
                    if(id == -1){
                        ctx.result("{\"success\": false}");
                        return;
                    }
                    boolean deletedPlayer = dbTableManager.deletePlayer(id);
                    ctx.result("{\"success\": " + deletedPlayer + "}");
                })

                .put("/players/{name}", ctx -> {
                    String name = ctx.pathParam("name");
                    ctx.contentType("application/json");

                    //TODO: refactor
                    Roster roster = dbTableManager.getRoster();
                    Player player = roster.getPlayerByName(name);
                    int id = dbTableManager.getPlayerID(player);
                    if(id == -1){
                        ctx.result("{\"success\": false}");
                        return;
                    }
                    boolean archivedPlayer = dbTableManager.archivePlayer(id);
                    ctx.result("{\"success\": " + archivedPlayer + "}");
                })


                .get("/hello", ctx -> ctx.render("hello.jte"))
                .start(7070);

    }
}

