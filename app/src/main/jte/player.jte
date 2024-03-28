@param com.mu_bball_stats.database.Player player

@template.page(
    title = player.getName(),
    content = @`
  <div>
      <h1>${player.getName()}</h1>
      <p>${player.getNumber()}</p>
      <p>${player.getPosition()}</p>
  </div>`
)
