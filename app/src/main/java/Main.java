import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.javalin.Javalin;
import io.javalin.config.Key;
import io.javalin.rendering.FileRenderer;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;


import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.mu_bball_stats.database.DBTableManager;
import com.mu_bball_stats.database.DatabaseManager;
import com.mu_bball_stats.web.Page;
import com.mu_bball_stats.model.Player;
import com.mu_bball_stats.model.PlayerStat;
import com.mu_bball_stats.model.Roster;
import com.mu_bball_stats.model.Session;
import com.mu_bball_stats.model.SessionStat;
import com.mu_bball_stats.WebBrowser;

public class Main {
    private static final String RESOURCE_ROOT = "src/main/resources/public";
    private static final boolean isDev = false;

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
        DatabaseManager.createSessionTable(conn);

        final DBTableManager dbTableManager = new DBTableManager(conn);

        Key<Roster> rosterKey = new Key<>("Roster");
        Key<Page> rosterPageKey = new Key<>("RosterPage");
        Key<Page> playerPageKey = new Key<>("PlayerPage");
        Key<Page> addPlayerStatsPageKey = new Key<>("AddPlayerStatsPage");
        Key<Page> statsPageKey = new Key<>("StatsPage");
        Key<Page> addPlayerPageKey = new Key<>("AddPlayerPage");
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("public");
            TemplateEngine engine = null;
            if(isDev){
                Path targetDirectory = Path.of("src/main/jte");
                System.out.println("targetDirectory: " + targetDirectory);
                engine = TemplateEngine.create(new DirectoryCodeResolver(targetDirectory), ContentType.Html);
            }
            else {
                Path targetDirectory = Path.of("templates");
                System.out.println("targetDirectory: " + targetDirectory);
                engine = TemplateEngine.createPrecompiled(targetDirectory, ContentType.Html);
            }
            FileRenderer jte = new JavalinJte(engine);
            config.fileRenderer(jte);

            Roster roster = dbTableManager.getRoster();
            Page rosterPage = new Page("Roster");
            rosterPage.addScript("playerFunctions.js");
            rosterPage.addScript("filters.js");
            Page playerPage = new Page("Player");
            playerPage.addScript("filters.js");
            Page addPlayerStatsPage = new Page("Add Player Stats");
            addPlayerStatsPage.addScript("playerFunctions.js");
            addPlayerStatsPage.addScript("add_stats.js");
            Page statsPage = new Page("Stats");
            statsPage.addScript("filters.js");
            Page addPlayerPage = new Page("Add Player");
            config.appData(rosterKey, roster);
            config.appData(rosterPageKey, rosterPage);
            config.appData(playerPageKey, playerPage);
            config.appData(addPlayerStatsPageKey, addPlayerStatsPage);
            config.appData(statsPageKey, statsPage);
            config.appData(addPlayerPageKey, addPlayerPage);
        })
                .get("/api/roster", ctx -> {
                    Roster roster = dbTableManager.getRoster();
                    ctx.contentType("application/json");
                    ctx.result(roster.toJson());
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
                .get("/add-stats", ctx -> {
                    ctx.render("add_stats.jte",
                    Map.of("page", ctx.appData(addPlayerStatsPageKey), "roster", ctx.appData(rosterKey)));
            })

            //updated API
            .get("/add-player", ctx -> {
                    ctx.render("add_player.jte", Map.of("page", ctx.appData(addPlayerPageKey)));
            })
            .post("/players", ctx -> {
                Player player = ctx.bodyAsClass(Player.class);
                ctx.contentType("application/json");
                boolean addedPlayer = dbTableManager.addPlayer(player);
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
                Player player = ctx.appData(rosterKey).getPlayerByID(id);
                if(player != null){
                    Page playerPage = ctx.appData(playerPageKey);
                    playerPage.setTitle(player.getName() + "'s Stats");
                    List<Session> sessions = dbTableManager.getPlayerStats(id);
                    if(sessions == null){
                        System.err.println("unable to find player with id " + id);
                        ctx.status(400);
                        ctx.result("{\"error\": \"unable to find player with id " + id + "\"}");
                        return;
                    }
                    ctx.contentType("text/html");
                    ctx.render("player.jte", Map.of("player", player, "page", playerPage, "sessions", sessions));
                }
                else {
                    //TODO: check if correct status code
                    ctx.status(404);
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
                    dbTableManager.updatePlayer(
                            player.getID(),
                            player.getName(),
                            player.getPosition(),
                            player.getNumber(),
                            isActive,
                            player.getClassYear()
                    );
                    ctx.result("{\"active\": \"" + isActive +"\"}");
                }
            })
            .post("/players/stats", ctx -> {
               SessionStat sessionStat = ctx.bodyAsClass(SessionStat.class); //fix this
               int sessionID = dbTableManager.createSession(sessionStat.getDate());
               Session session = dbTableManager.getSession(sessionID);
               for(Integer id : sessionStat.getStats().keySet()){
                    PlayerStat playerStat = sessionStat.getStats().get(id);
                    int statID = dbTableManager.addPlayerStats(id, playerStat, session);
                    if(statID == -1) continue;
               }
               ctx.contentType("application/json");
            })
            .get("/stats", ctx -> {
               List<Session> sessions = dbTableManager.getSessions();
            //    for(Session session : sessions){
            //         for(Player player : session.getPlayers()){
            //             System.out.println(player.getName() + " is " + player.isPlaying());
            //         }
            //    }
               ctx.contentType("text/html");
               ctx.render("stats.jte",
               Map.of("sessions", sessions, "page", ctx.appData(statsPageKey)));
            });
            app.start(7070);

            if(!isDev){
                new WebBrowser().main(args);
            }
    }
}
