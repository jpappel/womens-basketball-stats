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


function getPlayerStats(){
    fetch("/players/stats")
        .then(response => response.json())
        .then(data => {
            console.log(data);
        });
}