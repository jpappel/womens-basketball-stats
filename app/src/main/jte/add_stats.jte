@import com.mu_bball_stats.web.Page
@import com.mu_bball_stats.model.Player
@import com.mu_bball_stats.model.Roster
@import java.time.LocalDate;

@param Page page
@param Roster roster

@template.page(
    page = page,
    content = @`
        <h2>Add Player Stats</h2>
        <div>
        <label for="date">Date:</label>
        <input type="date" id="date" value="${LocalDate.now().toString()}"/>
        </div>
        <select id="statType">
        <option value="freeThrow">Free Throws</option>
        <option value="threePoint">Three Points</option>
        </select>
        <table id="playerStatsTable">
        <thead>
            <tr>
                <th>Player</th>
                <th>Attempted</th>
                <th>Made</th>
            </tr>
        </thead>
        @for(Player player : roster.getPlayers())
            @if(player.isPlaying())
                <tr>
                    <td data-player-id="${player.getID()}">${player.getName()}</td>
                    <td><input type="number" inputmode="numeric" pattern="[0-9]*" name="attempted_${player.getID()}" /></td>
                    <td><input type="number" inputmode="numeric" pattern="[0-9]*" name="made_${player.getID()}" /></td>
                </tr>
            @endif
        @endfor
        </table>
        <button type="submit" id="submitButton">Submit</button>
        <script>
            document.getElementById('submitButton').addEventListener('click', function() {
            let data = {};
            let sessionStats = {};
            const table = document.getElementById('playerStatsTable');
            let rows = table.getElementsByTagName('tr');
            for (let i = 1; i < rows.length; i++) { // Start from 1 to skip the header row
                let cells = rows[i].getElementsByTagName('td');
                let playerId = cells[0].getAttribute('data-player-id');
                let attempted = parseInt(cells[1].getElementsByTagName('input')[0].value);
                let made = parseInt(cells[2].getElementsByTagName('input')[0].value);
                sessionStats[playerId] = {
                    statType: document.getElementById('statType').value,
                    made: made,
                    attempted: attempted
                };
                data.date = document.getElementById('date').value;
                data.stats = sessionStats;
            }
            addPlayersStats(data);
        });
    </script>
    `
)