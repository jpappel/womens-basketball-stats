package com.mu_bball_stats.database;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStat {
    private int threePointersMade;
    private int threePointersAttempted;
    private int freeThrowsMade;
    private int freeThrowAttempts;

    public PlayerStat() {
        this.threePointersMade = 0;
        this.threePointersAttempted = 0;
        this.freeThrowsMade = 0;
        this.freeThrowAttempts = 0;
    }

    @JsonCreator
    public PlayerStat(@JsonProperty("threePointersMade") int threePointersMade,
                      @JsonProperty("threePointersAttempted") int threePointersAttempted,
                      @JsonProperty("freeThrowsMade") int freeThrowsMade,
                      @JsonProperty("freeThrowAttempts") int freeThrowAttempts) {
        this.threePointersMade = threePointersMade >= 0 ? threePointersMade : 0;
        this.threePointersAttempted = threePointersAttempted >= 0 ? threePointersAttempted : 0;
        this.freeThrowsMade = freeThrowsMade >= 0 ? freeThrowsMade : 0;
        this.freeThrowAttempts = freeThrowAttempts >= 0 ? freeThrowAttempts : 0;
    }

    public int getThreePointersMade() {
        return threePointersMade;
    }

    public void setThreePointersMade(int threePointersMade) {
        this.threePointersMade = threePointersMade >= 0 ? threePointersMade : this.threePointersMade;
    }

    public int getThreePointersAttempted() {
        return threePointersAttempted;
    }

    public void setThreePointersAttempted(int threePointersAttempted) {
        this.threePointersAttempted = threePointersAttempted >= 0 ? threePointersAttempted : this.threePointersAttempted;
    }

    public int getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public void setFreeThrowsMade(int freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade >= 0 ? freeThrowsMade : this.freeThrowsMade;
    }

    public int getFreeThrowAttempts() {
        return freeThrowAttempts;
    }

    public void setFreeThrowAttempts(int freeThrowAttempts) {
        this.freeThrowAttempts = freeThrowAttempts;
    }

    public double getThreePointPercentage() {
        return threePointersAttempted == 0 ? 0 : (double) threePointersMade / threePointersAttempted;
    }

    public double getFreeThrowPercentage() {
        return freeThrowAttempts == 0 ? 0 : (double) freeThrowsMade / freeThrowAttempts;
    }

}
