package com.mu_bball_stats.database;

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

}
