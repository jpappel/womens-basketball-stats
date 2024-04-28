package com.mu_bball_stats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a player in a basketball team.
 */
public class Player implements java.io.Serializable, Comparable<Player> {
    private int id;
    private String name;
    private String position;
    private int number;
    private boolean isPlaying;
    private int classYear;

    @JsonCreator
    public Player(@JsonProperty("name") String name, @JsonProperty("position") String position,
                  @JsonProperty("number") int number, @JsonProperty("classYear") int classYear) {
        this.id = -1;
        this.name = name;
        this.position = position;
        this.number = number;
        this.isPlaying = true;
        this.classYear = classYear;
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
     * Returns the class year of the player.
     *
     * @return the class year of the player
     */
    public int getClassYear() { return classYear; }

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
     * Sets the class year of the player.
     *
     * @param classYear the class year of the player
     */
    public void setClassYear(int classYear) { this.classYear = classYear; }

    public void setID(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    /**
     * Returns whether the player is playing or not.
     *
     * @return true if the player is playing, false otherwise
     */
    @JsonProperty("isPlaying")
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Sets the playing status of the player.
     * 
     * @param isPlaying true if the player is currently playing, false otherwise
     */
    public void setPlaying(boolean isPlaying){
        this.isPlaying = isPlaying;
    }

    /**
     * Converts the player object to JSON format.
     *
     * @return the player object in JSON format
     */
    public String toJson() {
        return String.format("{\"name\": \"%s\", \"position\": \"%s\", \"number\": %d, \"classYear\": %d}",
                             name, position, number, classYear);
    }

    @Override
    public int compareTo(Player otherPlayer) {
        if (this.isPlaying && !otherPlayer.isPlaying) {
            return -1;
        } else if (!this.isPlaying && otherPlayer.isPlaying) {
            return 1;
        } else {
            return this.id - otherPlayer.id;
        }
    }
}
