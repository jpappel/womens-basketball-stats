@import java.util.Map
@import com.mu_bball_stats.model.Player
@import com.mu_bball_stats.model.PlayerStat
@import com.mu_bball_stats.web.Page

@param Page page
@param Player player

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
        <th>Stat ID</th>
        <th>Free Throws Attempted</th>
        <th>Free Throws Made</th>
        <th>Free Throw Percentage</th>
        <th>Three Pointers Attempted</th>
        <th>Three Pointers Made</th>
        <th>Three Point Percentage</th>
      </tr>
      <tbody>
      @for(Map.Entry<Integer,PlayerStat> entry : player.stats.entrySet())
        <tr>
          <td>${entry.getKey()}</td>
          <td>${entry.getValue().getFreeThrowAttempts()}</td>
          <td>${entry.getValue().getFreeThrowsMade()}</td>
          <td>${entry.getValue().getFreeThrowPercentage()}</td>
          <td>${entry.getValue().getThreePointersAttempted()}</td>
          <td>${entry.getValue().getThreePointersMade()}</td>
          <td>${entry.getValue().getThreePointPercentage()}</td>
        </tr>
        @else
        <tr>
          <td colspan="7">No stats found</td>
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
