function togglePlayerActivity(id, newActivity){
    fetch("/players/" + id, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({active: newActivity})
    }).then(response => {
        if(response.ok){
            location.reload();
        } else {
            alert("Failed to toggle player activity");
        }
    });
}