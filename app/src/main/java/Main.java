import java.nio.file.Files;
import java.nio.file.Paths;

import io.javalin.Javalin;
import io.javalin.config.Key;
import io.javalin.rendering.template.JavalinJte;

import com.google.gson.Gson;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.mu_bball_stats.database.*;
import com.mu_bball_stats.web.*;

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

        Key<Roster> rosterKey = new Key<>("Roster");
        Key<Page> rosterPageKey = new Key<>("RosterPage");
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("public");
            config.fileRenderer(new JavalinJte());
            Roster roster = dbTableManager.getRoster();
            Page rosterPage = new Page("Roster");
            rosterPage.addScript("playerFunctions.js");
            config.appData(rosterKey, roster);
            config.appData(rosterPageKey, rosterPage);
        })
                .get("/api/roster", ctx -> {
                    Roster roster = dbTableManager.getRoster();
                    ctx.contentType("application/json");
                    ctx.result(roster.toJson());
                })
                //.post("/add-player", ctx -> {
                //    Player player = ctx.bodyAsClass(Player.class);
                //    ctx.contentType("application/json");
                //    boolean addedPlayer = dbTableManager.addPlayer(player);
                //    ctx.result("{\"success\": " + addedPlayer + "}");
                //})
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
                .get("/hello", ctx -> ctx.render("hello.jte"))
                .get("/add-stats", ctx -> {
                    ctx.render("add_stats.jte",
                    Map.of("title", "Add Statistics","heading", "Add Stats", "content", "content here"));
            })

            //updated API
            .get("/add-player", ctx -> {
                    byte[] htmlContent = Files.readAllBytes(Paths.get(RESOURCE_ROOT, "add_player.html"));
                    ctx.contentType("text/html");
                    ctx.result(htmlContent);
            })
            .post("/players", ctx -> {
                Player player = ctx.bodyAsClass(Player.class);
                ctx.contentType("application/json");
                boolean addedPlayer = dbTableManager.addPlayer(player);
                System.out.println(addedPlayer);
                if(addedPlayer){
                    ctx.status(201);
                    player.setID(dbTableManager.getPlayerID(player));
                    Roster roster = ctx.appData(rosterKey);
                    roster.addPlayer(player);
                }
                else {
                    ctx.status(400);
                }
                ctx.result("{\"success\": " + addedPlayer + "}");
            })
            .get("/roster", ctx -> {
                Roster roster = ctx.appData(rosterKey);
                Page rosterPage = ctx.appData(rosterPageKey);
                ctx.contentType("text/html");
                ctx.render("roster.jte", Map.of("roster", roster, "page", rosterPage));
            })
            .get("/players/{id}", ctx -> {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Roster roster = ctx.appData(rosterKey);
                Player player = roster.getPlayerByID(id);
                if(player != null){
                    ctx.render("player.jte", Map.of("player", player));
                }
                else {
                    //TODO: check if correct status code
                    ctx.status(400);
                    ctx.result("{\"not impelemented\": \"yet\"}");
                }
            })
            .post("/players/{id}/stats", ctx -> {
                ctx.result("{\"not impelemented\": \"yet\"}");
            })
            .patch("/players/{id}", ctx -> {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Roster roster = ctx.appData(rosterKey);
                Player player = roster.getPlayerByID(id);

                ctx.contentType("application/json");
                if(player == null){
                    ctx.status(400);
                    ctx.result("{\"error\": unable to find player with id " + id + "}");
                }
                else {
                    Map<String, List<String>> payload = ctx.queryParamMap();
                    boolean isActive = Boolean.parseBoolean(payload.getOrDefault("active", List.of("true")).get(0));
                    player.setPlaying(isActive);
                    ctx.result("{\"active\": \"" + isActive +"\"}");
                }
            })
            //dummy page
            .get("/page", ctx -> {
                ctx.result("{\"not impelemented\": \"yet\"}");
            });
                app.start(7070);


    }
}
