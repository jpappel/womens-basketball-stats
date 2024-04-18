package com.mu_bball_stats.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public class Session implements Comparable<Session> {
    LocalDate date;
    int ID;
    TreeMap<Player, ArrayList<PlayerStat>> playerStats;

    public Session(LocalDate date, int ID) {
        this.date = date;
        this.ID = ID;
        this.playerStats = new TreeMap<>();
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

    public LocalDate getDate() {
        return date;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(playerStats.keySet());
    }

    @Override
    public int compareTo(Session other) {
        int dateCompare = this.date.compareTo(other.date);
        return dateCompare != 0 ? dateCompare : Integer.compare(this.ID, other.ID);
    }
}



