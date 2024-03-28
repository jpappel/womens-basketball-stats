package com.mu_bball_stats.database;

import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SessionStat {

    protected Map<Integer, PlayerStat> playerStats;

    public SessionStat() {
        this.playerStats = new HashMap<>();
    }

    @JsonCreator
    public SessionStat(Map<Integer, PlayerStat> playerStats) {
        this.playerStats = playerStats != null ? playerStats : new HashMap<>();
    }

    public Map<Integer, PlayerStat> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(Map<Integer, PlayerStat> playerStats) {
        this.playerStats = playerStats != null ? playerStats : new HashMap<>();
    }

}
