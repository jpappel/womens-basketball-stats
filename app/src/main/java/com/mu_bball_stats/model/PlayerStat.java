package com.mu_bball_stats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStat {
    String statType;
    int made;
    int attempted;

    public PlayerStat(String statType, int made, int attempted) {
        if (!statType.equals("threePoint") && !statType.equals("freeThrow")) {
            System.err.println("Invalid Stat Type " + statType);
            throw new IllegalArgumentException("Invalid Stat Type");
        }
        this.statType = statType;
        this.made = made;
        this.attempted = attempted;
    }

    @JsonCreator
    public PlayerStat(
            @JsonProperty("made") int made,
            @JsonProperty("attempted") int attempted,
            @JsonProperty("statType") String statType) {
        this.made = made;
        this.attempted = attempted;
        this.statType = statType;
    }

    public double getPercentage() {
        return attempted == 0 ? 0 : (double) made / attempted;
    }

    public String getFormattedPercentage() {
        return String.format("%.1f%%", getPercentage() * 100);
    }

    public int getMade() {
        return made;
    }

    public int getAttempted() {
        return attempted;
    }

    public String getStatType() {
        return statType;
    }

    @Override
    public String toString() {
        return "PlayerStat{" +
                "statType='" + statType + '\'' +
                ", made=" + made +
                ", attempted=" + attempted +
                '}';
    }
}
