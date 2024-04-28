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
  <div>
      <h1>${player.getName()}</h1>
      <div>
      <span>Number: ${player.getNumber()}</span>
      </div>
      <div>
      <span>Postion: ${player.getPosition()}</span>
      </div>
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
                        <td>${stat.getPercentage()}</td>
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
  <script>
  $(function(){
        $('table').tablesorter();

    })
  </script>
  `
)
