package com.mu_bball_stats.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStat {
    String StatType;
    int made;
    int attempted;

    public PlayerStat(String statType, int made, int attempted) {
        if (statType == "TP") {
            this.StatType = "Three Point";
        } 
        else if (statType == "FT") {
            this.StatType = "Free Throw";
        }
        else {
            throw new IllegalArgumentException("Invalid Stat Type");
        }
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
        this.StatType = statType;
    }

    public double getPercentage() {
        return attempted == 0 ? 0 : (double) made / attempted;
    }

    public int getMade() {
        return made;
    }

    public int getAttempted() {
        return attempted;
    }
}
