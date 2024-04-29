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
        <input type="text" id="playerSearch" class="filterInput" placeholder="Search for players..." data-column="0">
    </div>
    <div class="rosterFilter">
        <label for="dateFilter">Date Range</label>
        <input type="text" id="dateRange" class="filterInput" data-column="1">
    </div>
    <div class="rosterFilter">
        <label for="statTypeFilter">Stat Type</label>
        <select id="statTypeFilter" class="filterInput" data-column="2">
            <option value="">All</option>
            <option value="freeThrow">Free Throw</option>
            <option value="threePoint">Three Point</option>
        </select>
    </div>
    <div class="rosterFilter">
        <label for="activityFilter">Activity Level</label>
        <select id="activityFilter" class="filterInput" data-column="6">
            <option value="">All</option>
            <option value="Yes">Active</option>
            <option value="No">Inactive</option>
        </select>
    </div>
    <button id="resetFilters">Clear Filters</button>
    </fieldset>
    <table class="tablesorter">
        <thead>
            <th>Player</th>
            <th class="sorter-date-range-YYYYMMDD">Date</th>
            <th>Type</th>
            <th>Made</th>
            <th>Attempted</th>
            <th>Percentage</th>
            <th>Active</th>
        </thead>
        <tbody>
        @for(Session session : sessions)
            @for(Player player : session.getPlayerStats().keySet())
                @for(PlayerStat stat : session.getPlayerStats().get(player))
                    <tr>
                        <td>${player.getName()}</td>
                        <td>${session.getDate().toString()}</td>
                        <td>${stat.getStatType()}</td>
                        <td>${stat.getMade()}</td>
                        <td>${stat.getAttempted()}</td>
                        <td>${stat.getFormattedPercentage()}</td>
                        <td>${player.isPlaying() ? "Yes" : "No"}</td>
                    </tr>
                @endfor
            @endfor
        @endfor
        </tbody>
    </table>
    `
)
