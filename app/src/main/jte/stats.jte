@import java.util.List
@import com.mu_bball_stats.web.Page
@import com.mu_bball_stats.model.Player
@import com.mu_bball_stats.model.PlayerStat
@import com.mu_bball_stats.model.Session

@param Page page
@param List<Session> sessions

@template.page(
    page = page,
    content = @`
<h2>Statistics</h2>
    <fieldset class="rosterFilters">
    <legend>Stats Filters</legend>
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
            <th>Player</th>
            <th>Date</th>
            <th>Type</th>
            <th>Made</th>
            <th>Attempted</th>
            <th>Percentage</th>
        </thead>
        <tbody>
        @for(Session session : sessions)
            @for(Player player : session.getPlayerStats().keySet())
                @for(PlayerStat stat : session.getPlayerStats().get(player))
                    <tr class="${player.isPlaying() ? "" : "inactive"}">
                        <td>${player.getName()}</td>
                        <td>${session.getDate().toString()}</td>
                        <td>${stat.getStatType()}</td>
                        <td>${stat.getMade()}</td>
                        <td>${stat.getAttempted()}</td>
                        <td>${stat.getPercentage()}</td>
                    </tr>
                @endfor
            @endfor
        @endfor
        </tbody>
    </table>
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
