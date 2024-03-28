import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a player in a basketball team.
 */
public class Player implements java.io.Serializable{
    private String name;
    private String position;
    private int number;
    private int threePointersMade;
    private int threePointersAttempted;
    private int freeThrowsMade;
    private int freeThrowAttempts;
    private int isPlaying;

    @JsonCreator
    public Player(@JsonProperty("name") String name, @JsonProperty("position") String position,
                  @JsonProperty("number") int number, @JsonProperty("isPlaying") int isPlaying){
        this.name = name;
        this.position = position;
        this.number = number;
        this.isPlaying = isPlaying;
        this.threePointersMade = 0;
        this.threePointersAttempted = 0;
        this.freeThrowsMade = 0;
        this.freeThrowAttempts = 0;

    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the position of the player.
     *
     * @return the position of the player
     */
    public String getPosition() {
        return position;
    }

    /**
     * Returns the number of the player.
     *
     * @return the number of the player
     */
    public int getNumber() {
        return number;
    }

     /**
     * Gets the total number of successful three-point shots made by the player.
     * 
     * @return the number of three-pointers made
     */
    public int getThreePointersMade() {
        return threePointersMade;
    }

    /**
     * Gets the total number of three-point shots attempted by the player.
     * 
     * @return the number of three-pointer attempts
     */
    public int getThreePointersAttempted() {
        return threePointersAttempted;
    }

    /**
     * Gets the total number of successful free throw shots made by the player.
     * 
     * @return the number of free throws made
     */
    public int getFreeThrowsMade() {
        return freeThrowsMade;
    }

    /**
     * Gets the total number of free throw shots attempted by the player.
     * 
     * @return the number of free throw attempts
     */
    public int getFreeThrowAttempts() {
        return freeThrowAttempts;
    }

    /**
     * Sets the name of the player.
     *
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }
    

    /**
     * Sets the position of the player.
     *
     * @param position the position of the player
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Sets the number of the player.
     *
     * @param number the number of the player
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns whether the player is playing or not.
     *
     * @return true if the player is playing, false otherwise
     */
    public int isPlaying() {
        return isPlaying;
    }

    public void setActive(){
        this.isPlaying = 1;
    }

    public void setInactive(){
        this.isPlaying = 0;
    }
    /**
     * Updates the total number of successful three-point shots made.
     * 
     * @param threePointersMade the new total of three-pointers made
     */
    public void setThreePointersMade(int threePointersMade) {
        this.threePointersMade = threePointersMade;
    }

    /**
     * Updates the total number of three-point shots attempted.
     * 
     * @param threePointersAttempted the new total of three-pointer attempts
     */
    public void setThreePointersAttempted(int threePointersAttempted) {
        this.threePointersAttempted = threePointersAttempted;
    }

    /**
     * Updates the total number of successful free throw shots made.
     * 
     * @param freeThrowsMade the new total of free throws made
     */
    public void setFreeThrowsMade(int freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
    }

    /**
     * Updates the total number of free throw shots attempted.
     * 
     * @param freeThrowAttempts the new total of free throw attempts
     */
    public void setFreeThrowAttempts(int freeThrowAttempts) {
        this.freeThrowAttempts = freeThrowAttempts;
    }

    /**
     * Converts the player object to JSON format.
     *
     * @return the player object in JSON format
     */
    public String toJson() {
        return String.format("{\"name\": \"%s\", \"position\": \"%s\", \"number\": %d, \"threePointersMade\": %d, \"threePointersAttempted\": %d, \"freeThrowsMade\": %d, \"freeThrowAttempts\": %d}", 
                             name, position, number, threePointersMade, threePointersAttempted, freeThrowsMade, freeThrowAttempts);
    }
}
