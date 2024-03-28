@import com.mu_bball_stats.web.Page
@import com.mu_bball_stats.database.Player
@import com.mu_bball_stats.database.Roster

@param Page page
@param Roster roster

@template.page(
    page = page,
    content = @`
        <h2>Add Player Stats</h2>
        <table id="playerStatsTable">
        <thead>
        <tr>
            <th>Player ID</th>
            <th>Player</th>
            <th>Free Throws Attempted</th>
            <th>Free Throws Made</th>
            <th>Three Pointers Attempted</th>
            <th>Three Pointers Made</th>
        </tr>
        </thead>
        @for(Player player : roster.getPlayers())
            @if(player.isPlaying())
                <tr>
                    <td>${player.getID()}</td>
                    <td>${player.getName()}</td>
                    <td><input type="number" inputmode="numeric" pattern="[0-9]*" name="fta_${player.getID()}" /></td>
                    <td><input type="number" inputmode="numeric" pattern="[0-9]*" name="ftm_${player.getID()}" /></td>
                    <td><input type="number" inputmode="numeric" pattern="[0-9]*" name="tpa_${player.getID()}" /></td>
                    <td><input type="number" inputmode="numeric" pattern="[0-9]*" name="tpm_${player.getID()}" /></td>
                </tr>
            @endif
        @endfor
        </table>
        <button type="submit" id="submitButton">Submit</button>
        <script>
            document.getElementById('submitButton').addEventListener('click', function() {
            let sessionStats = {};
            const table = document.getElementById('playerStatsTable');
            let rows = table.getElementsByTagName('tr');
            for (let i = 1; i < rows.length; i++) { // Start from 1 to skip the header row
                let cells = rows[i].getElementsByTagName('td');
                let playerId = cells[0].textContent;
                let fta = parseInt(cells[2].getElementsByTagName('input')[0].value);
                let ftm = parseInt(cells[3].getElementsByTagName('input')[0].value);
                let tpa = parseInt(cells[4].getElementsByTagName('input')[0].value);
                let tpm = parseInt(cells[5].getElementsByTagName('input')[0].value);
                sessionStats[playerId] = {
                    "threePointersMade": tpm,
                    "threePointersAttempted": tpa,
                    "freeThrowsMade": ftm,
                    "freeThrowAttempts": fta
                };
            }
            addPlayersStats(sessionStats);
        });
    </script>
    `
)