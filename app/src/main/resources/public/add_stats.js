function addPlayersStats(sessionStats) {
    fetch("/players/stats", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(sessionStats)
    }).then(response => {
        if (response.ok) {
            alert("Players stats added successfully");
            location.reload();
        } else {
            alert("Failed to add players stats");
        }
    });
}

document.getElementById('submitButton').addEventListener('click', function () {
    const forms = document.getElementsByClassName("playerStatsForm");
    for (let form of forms) {
        const table = form.getElementsByClassName('playerStatsTable')[0];
        let data = {};
        let sessionStats = {};
        let rows = table.getElementsByTagName('tr');
        for (let i = 1; i < rows.length; i++) { // Start from 1 to skip the header row
            let cells = rows[i].getElementsByTagName('td');
            let playerId = cells[0].getAttribute('data-player-id');
            let attempted = parseInt(cells[1].getElementsByTagName('input')[0].value);
            let made = parseInt(cells[2].getElementsByTagName('input')[0].value);
            sessionStats[playerId] = {
                statType: form.getElementsByClassName('statType')[0].value,
                made: made,
                attempted: attempted
            };
            data.date = document.getElementById('date').value;
            data.stats = sessionStats;
        }
        console.log(data);
        addPlayersStats(data);
    }
});