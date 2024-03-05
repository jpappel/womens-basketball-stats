import java.util.ArrayList;

/**
 * Represents a roster of players
 */
public class Roster {
    ArrayList<Player> roster;

    public Roster(){
        roster = new ArrayList<Player>();
    }
    
    /**
     * Adds a player to the roster.
     *
     * @param name     the name of the player
     * @param position the position of the player
     * @param number   the number of the player
     */
    public void addPlayer(String name, String position, int number){
        roster.add(new Player(name, position, number));
    }

    /**
     * Removes a player from the roster.
     *
     * @param player the player to be removed
     */
    public void removePlayer(Player player){
        roster.remove(player);
    }

    /**
     * Retrieves a player from the roster by their name.
     *
     * @param name the name of the player
     * @return the player with the specified name, or null if not found
     */
    public Player getPlayerByName(String name){
        for (Player player : roster){
            if (player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

}