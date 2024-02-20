import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a player in a basketball team.
 */
public class Player implements java.io.Serializable{
    private String name;
    private String position;
    private int number;

    @JsonCreator
    public Player(@JsonProperty("name") String name, @JsonProperty("position") String position,
                  @JsonProperty("number") int number) {
        this.name = name;
        this.position = position;
        this.number = number;
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
     * Converts the player object to JSON format.
     *
     * @return the player object in JSON format
     */
    public String toJson() {
        return String.format("{\"name\": \"%s\", \"position\": \"%s\", \"number\": \"%s\"}", name, position, number);
    }
}
