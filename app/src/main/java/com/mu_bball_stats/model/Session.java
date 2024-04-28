package com.mu_bball_stats.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;

public class Session implements Comparable<Session> {
    LocalDate date;
    int ID;
    int drillNum;
    Map<Player, ArrayList<PlayerStat>> playerStats;

    public Session(LocalDate date, int ID, int drillNum) {
        this.date = date;
        this.drillNum = drillNum;
        this.ID = ID;
        this.playerStats = new HashMap<>();
    }

    public Session(LocalDate date, int ID, int drillNum, HashMap<Player, ArrayList<PlayerStat>> playerStats) {
        this.date = date;
        this.ID = ID;
        this.drillNum = drillNum;
        this.playerStats = playerStats;
    }

    public String getSeason(){
        if (date.getMonthValue() >= 9 || date.getMonthValue() <= 12) {
            return date.getYear() + "-" + (date.getYear() + 1);
        } else {
            return (date.getYear() - 1) + "-" + date.getYear();
        }
    }

    public void addPlayer(Player player){
        playerStats.put(player, new ArrayList<>());
    }

    public void addStat(Player player, PlayerStat stat){
        if (playerStats.containsKey(player)){
            playerStats.get(player).add(stat);
        }
        else{
            ArrayList<PlayerStat> stats = new ArrayList<>();
            stats.add(stat);
            playerStats.put(player, stats);
        }
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getID() {
        return ID;
    }

    public int getDrillNum(){
        return drillNum;
    }

    public Map<Player, ArrayList<PlayerStat>> getPlayerStats() {
        return playerStats;
    }

    public Collection<ArrayList<PlayerStat>> getPlayerStatsValues(){
        return playerStats.values();
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(playerStats.keySet());
    }

    @Override
    public int compareTo(Session other) {
        int dateCompare = this.date.compareTo(other.date);
        return dateCompare != 0 ? dateCompare : Integer.compare(this.ID, other.ID);
    }

    @Override
    public String toString() {
        return "Session{" +
                "date=" + date +
                ", ID=" + ID +
                ", drillNum=" + drillNum +
                ", playerStats=" + playerStats +
                '}';
    }
}



