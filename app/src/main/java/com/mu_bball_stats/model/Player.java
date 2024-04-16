package com.mu_bball_stats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.TreeMap;

/**
 * Represents a player in a basketball team.
 */
public class Player implements java.io.Serializable{
    private int id;
    private String name;
    private String position;
    private int number;
    private boolean isPlaying;
    public TreeMap<Integer, PlayerStat> stats;

    @JsonCreator
    public Player(@JsonProperty("name") String name, @JsonProperty("position") String position,
                  @JsonProperty("number") int number) {
        this.id = -1;
        this.name = name;
        this.position = position;
        this.number = number;
        this.isPlaying = true;
        this.stats = new TreeMap<>();
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
     * Adds a player stat to the player's stats collection.
     *
     * @param statsID the ID of the player stat
     * @param stat the player stat to be added
     */
    public void addStat(int statsID, PlayerStat stat){
        stats.put(statsID, stat);
    }

    public void setStat(TreeMap<Integer, PlayerStat> stats){
        this.stats = stats;
    }

    public TreeMap<Integer, PlayerStat> getStats(){
        return stats;
    }

    /**
     * Converts the player object to JSON format.
     *
     * @return the player object in JSON format
     */
    public String toJson() {
        return String.format("{\"name\": \"%s\", \"position\": \"%s\", \"number\": %d}", 
                             name, position, number);
    }
}
