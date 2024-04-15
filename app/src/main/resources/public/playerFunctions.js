function togglePlayerActivity(id, newActivity){
    const params = new URLSearchParams();
    params.append("active", newActivity);

    fetch(`/players/${id}?${params.toString()}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(response => {
        if(response.ok){
            alert("Player activity toggled successfully");
            location.reload();
        } else {
            alert("Failed to toggle player activity");
        }
    });
}

function addPlayersStats(sessionStats){
    fetch("/players/stats", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(sessionStats)
    }).then(response => {
        if(response.ok){
            alert("Players stats added successfully");
            location.reload();
        } else {
            alert("Failed to add players stats");
        }
    });

function getPlayerStats(){
    fetch("/players/stats")
        .then(response => response.json())
        .then(data => {
            console.log(data);
        });
}
}