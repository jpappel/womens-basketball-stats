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
        <div class="playerStatsForm">
        <select class="statType">
        <option value="freeThrow">Free Throws</option>
        <option value="threePoint">Three Points</option>
        </select>
        <table class="playerStatsTable">
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
                    <td><input required type="number" inputmode="numeric" pattern="[0-9]*" name="attempted_${player.getID()}" /></td>
                    <td><input required type="number" inputmode="numeric" pattern="[0-9]*" name="made_${player.getID()}" /></td>
                </tr>
            @endif
        @endfor
        </table>
        </div>
        <div style="display: flex; padding: 10px;">
        <button style="margin: 4px" type="button" onclick="createStatsForm()">Add Another Drill</button>
        <button style="margin: 4px" type="button" onclick="removeStatsForm()">Remove Last Drill</button>
        </div>
        <button type="submit" id="submitButton">Submit</button>
        <script>
        function removeStatsForm(){
            let forms = document.querySelectorAll(".playerStatsForm");
            if(forms.length > 1){
                let lastForm = forms[forms.length - 1];
                lastForm.remove();
            }
        }
        function createStatsForm(){
            let form = document.createElement("div");
            form.classList.add("playerStatsForm");
            form.innerHTML = '\
            <select class="statType">\
            <option value="freeThrow">Free Throws</option>\
            <option value="threePoint">Three Points</option>\
            </select>\
            <table class="playerStatsTable">\
            <thead>\
            <tr>\
                <th>Player</th>\
                <th>Attempted</th>\
                <th>Made</th>\
            </tr>\
            </thead>\
            @for(Player player : roster.getPlayers())\
                @if(player.isPlaying())\
                    <tr>\
                    <td data-player-id="${player.getID()}">${player.getName()}</td>\
                    <td><input required type="number" inputmode="numeric" pattern="[0-9]*" name="attempted_${player.getID()}" /></td>\
                    <td><input required type="number" inputmode="numeric" pattern="[0-9]*" name="made_${player.getID()}" /></td>\
                    </tr>\
                @endif\
            @endfor\
            </table>\
        ';
        let forms = document.querySelectorAll(".playerStatsForm");
        let lastForm = forms[forms.length - 1];
        lastForm.insertAdjacentElement('afterend', form);
        }
        </script>
    `
)