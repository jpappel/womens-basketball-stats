@import com.mu_bball_stats.web.Page

@param Page page

@template.page(
    page = page,
    content = @`
    <h2>Add Player</h2>
    <form id="playerForm">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        
        <label for="year">Year:</label>
        <input type="number" inputmode="numeric" id="year" name="year" required><br><br>
        
        <label for="number">Number:</label>
        <input type="text" inputmode="numeric" pattern="\d*" id="number" name="number" required/><br><br>
        
        <label for="position">Position:</label>
        <select id="position" name="position" required>
            <option value="PG">Point Guard</option>
            <option value="SG">Shooting Guard</option>
            <option value="SF">Small Forward</option>
            <option value="PF">Power Forward</option>
            <option value="C">Center</option>
        </select><br><br>
        
        <input type="submit" value="Add Player">
    </form>

    <script>
        document.getElementById("playerForm").addEventListener("submit", function(event) {
            event.preventDefault();
            
            // Retrieve form data
            const name = document.getElementById("name").value;
            const year = document.getElementById("year").value;
            const number = document.getElementById("number").value;
            const position = document.getElementById("position").value;

            //input validation
            if (name == "" || year == "" || number == "" || position == "") {
                alert("Please fill out all fields.");
                return;
            }
            
            // Create player object
            const player = {
                name: name,
                classYear: year,
                number: number,
                position: position,
                isPlaying: 1
            };
            
            // Send player data to Java API using AJAX or fetch API
            // Replace the URL with the actual endpoint of your Java API
            fetch("/players", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(player)
            })
            .then(response => response.json())
            .then(data => {
                // Handle response from Java API
                console.log(data);
                alert("Player added successfully!");
                // Reset form
                document.getElementById("playerForm").reset();
            })
            .catch(error => {
                // Handle error
                console.error(error);
                alert("Failed to add player. Please try again.");
            });
        });
    </script>
    `
)