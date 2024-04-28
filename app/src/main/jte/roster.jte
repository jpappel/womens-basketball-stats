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
        <input type="text" id="playerSearch" placeholder="Search for players...">
    </div>
    <div class="rosterFilter">
        <input type="checkbox" id="activityFilter" checked>
        <label for="activityFilter">Show Inactive Players</label>
    </div>
    </fieldset>
    <table class="tablesorter">
        <thead>
            <tr>
                <th>Name</th>
                <th>Class</th>
                <th>Number</th>
                <th>Position</th>
                <th>Active</th>
                <th>Actions</th>
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
                <button onclick="editPlayer()">Edit</button>
                <button onclick="togglePlayerActivity(${player.getID()}, ${!player.isPlaying()})">${player.isPlaying() ? "Set Inactive" : "Set Active"}</button>
                <!--<button onclick="deletePlayer(&quot;${player.name}&quot;)">Delete</button>-->
            </td>
        </tr>
        @endfor
    </table>
    <a href="/add-player">Add Player</a>
    <script>
    document.getElementById('activityFilter').addEventListener('change', function() {
        let showInactive = this.checked;
        let inactiveRows = document.querySelectorAll('.inactive');
        inactiveRows.forEach(function(row) {
            row.style.display = showInactive ? '' : 'none';
        });
    });
    $(function(){
        $('table').tablesorter();

    })
    </script>
    `
)
