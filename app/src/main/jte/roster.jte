@import com.mu_bball_stats.model.Roster
@import com.mu_bball_stats.model.Player
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
        <input type="text" id="playerSearch" class="filterInput" placeholder="Search for players..." data-column="0">
    </div>
    <div class="rosterFilter">
        <input type="text" id="classFilter" class="filterInput" placeholder="Filter by class..." data-column="1">
    </div>
    <div class="rosterFilter">
        <label for="activityFilter">Activity Level</label>
        <select id="activityFilter" class="filterInput" data-column="4">
            <option value="">All</option>
            <option value="Yes">Active</option>
            <option value="No">Inactive</option>
        </select>
    </div>
    <button id="resetFilters">Clear Filters</button>
    </fieldset>
    <nav>
    <a href="/add-player">Add Player</a>
    </nav>
    <table class="tablesorter">
        <thead>
            <tr>
                <th>Name</th>
                <th>Class</th>
                <th>Number</th>
                <th>Position</th>
                <th>Active</th>
                <th data-sorter="false">Actions</th>
            </tr>
        </thead>
        @for(Player player : roster.getPlayers())
        <tr id="playerRow_${player.getID()}" class="${player.isPlaying() ? "" : "inactive"}">
            <td><a href="/players/${player.getID()}">${player.getName()}</a></td>
            <td>${player.getClassYear()}</td>
            <td>${player.getNumber()}</td>
            <td>${player.getPosition()}</td>
            <td>${player.isPlaying() ? "Yes" : "No"}</td>
            <td>
                <!--<button onclick="editPlayer()">Edit</button>-->
                <button onclick="togglePlayerActivity(${player.getID()}, ${!player.isPlaying()})">${player.isPlaying() ? "Set Inactive" : "Set Active"}</button>
                <!--<button onclick="deletePlayer(&quot;${player.name}&quot;)">Delete</button>-->
            </td>
        </tr>
        @endfor
    </table>
    `
)
