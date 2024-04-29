@import java.util.Map
@import com.mu_bball_stats.model.Player
@import com.mu_bball_stats.model.PlayerStat
@import com.mu_bball_stats.web.Page
@import java.util.List
@import com.mu_bball_stats.model.Session

@param Page page
@param Player player
@param List<Session> sessions

@template.page(
  page = page,
    content = @`
    <div class="player-info">
    <h1>${player.getName()}</h1>
    <h2>Class Year: ${player.getClassYear()}</h2>
        <span>Number: ${player.getNumber()}</span><br/>
        <span>Position: ${player.getPosition()}</span>
        <span>Active: ${player.isPlaying() ? "Yes" : "No"}</span>
  </div>
  <table class="tablesorter">
    <thead>
      <tr>
        <th>Date</th>
        <th>Stat Type</th>
        <th>Made</th>
        <th>Attempted</th>
        <th>Perecentage</th>
      </tr>
      <tbody>
      @for(Session session : sessions)
            @for(Player player : session.getPlayerStats().keySet())
                @for(PlayerStat stat : session.getPlayerStats().get(player))
                    <tr>
                        <td>${session.getDate().toString()}</td>
                        <td>${stat.getStatType()}</td>
                        <td>${stat.getMade()}</td>
                        <td>${stat.getAttempted()}</td>
                        <td>${stat.getFormattedPercentage()}</td>
                    </tr>
                @endfor
            @endfor
        @else
        <tr>
          <td colspan="5">No stats found</td>
        </tr>
        @endfor
      </tbody>
    </thead>
  </table>
  `
)
