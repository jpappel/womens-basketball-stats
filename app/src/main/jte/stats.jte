@import com.mu_bball_stats.web.Page
@import com.mu_bball_stats.model.Player
@import com.mu_bball_stats.model.Roster

@param Page page
@param Roster Roster


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
            <th>Stat id</th>
            <th>Player</th>
            <th>Free Throws Attempted</th>
            <th>Free Throws Made</th>
            <kjth>Free Throw Percentage</kjth>
            <th>Three Pointers Attempted</th>
            <th>Three Pointers Made</th>
            <th>Three Point Percentage</th>
        </thead>
        <tbdoy>
        @for(Player player : roster.getPlayers())
            @for(Map.Entry<Integer,PlayerStat> entry : player.stats.entrySet())
            <tr>
              <td>${entry.getKey()}</td>
              <td>${entry.getValue().getFreeThrowAttempts()}</td>
              <td>${entry.getValue().getFreeThrowsMade()}</td>
              <td>${entry.getValue().getFreeThrowPercentage()}</td>
              <td>${entry.getValue().getThreePointersAttempted()}</td>
              <td>${entry.getValue().getThreePointersMade()}</td>
              <td>${entry.getValue().getThreePointPercentage()}</td>
            </tr>
        @endfor
        @endfor
        </tbody>
    </table>
    `
)
