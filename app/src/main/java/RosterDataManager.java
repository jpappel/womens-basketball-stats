public interface RosterDataManager {
    public Player getPlayer(int ID);
    public void addPlayer(Player player);
    public Roster getRoster();
    public void updatePlayer(int ID, String name, String position, int number);
    public void deletePlayer(int ID);
}
