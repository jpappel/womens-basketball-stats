package com.mu_bball_stats.model;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionStat {
    private Map<Integer, PlayerStat> stats;
    private LocalDate date;

    @JsonCreator
    public SessionStat(@JsonProperty("stats") Map<Integer, PlayerStat> stats, @JsonProperty("date") LocalDate date) {
        this.stats = stats;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Integer, PlayerStat> getStats() {
        return stats;
    }
}
