@import com.mu_bball_stats.database.Roster
@import com.mu_bball_stats.database.Player
@import com.mu_bball_stats.web.Page

@param Page page
@param Roster roster

@template.page(
    page = page,
    content = @`
    <h2>Team Roster</h2>
    <fieldset class="rosterFilters">
    <legend>Roster Filters</legend>
    <div class="rosterFilter">
        <input type="text" id="playerSearch" placeholder="Search for players...">
    </div>
    <div class="rosterFilter">
        <input type="checkbox" id="activityFilter" checked>
        <label for="activityFilter">Show Inactive Players</label>
    </div>
    </fieldset>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Number</th>
                <th>Position</th>
                <th>Active</th>
                <th>Actions</th>
            </tr>
        </thead>
        @for(Player player : roster.getPlayers())
        <tr>
            <td>${player.getID()}</td>
            <td><a href="/players/${player.getID()}">${player.getName()}</a></td>
            <td>${player.getNumber()}</td>
            <td>${player.getPosition()}</td>
            <td>${player.isPlaying() ? "Yes" : "No"}</td>
            <td>
                <button onclick="editPlayer()">Edit</button>
                <button onclick="togglePlayerActivity(${player.getID()}, ${!player.isPlaying()})">${player.isPlaying() ? "Set Inactive" : "Set Activate"}</button>
                <!--<button onclick="deletePlayer(&quot;${player.name}&quot;)">Delete</button>-->
            </td>
        </tr>
        @endfor
    </table>
    <a href="/add-player">Add Player</a>
    `
)
