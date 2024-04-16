package com.mu_bball_stats.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class PracticeStats {
    ArrayList<Session> sessions;
    
    public PracticeStats() {
        // TODO Auto-generated constructor stub
    }

    public void createSession(int ID){
        Session session = new Session(LocalDate.now(), ID);
    }

    public void addPlayerToSession(Session session, Player player){
        session.addPlayer(player);
    }

    public void addStatToSession(Session session, Player player, PlayerStat stat){
        session.addStat(player, stat);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
}
