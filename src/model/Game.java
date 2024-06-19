package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controller.GameController;

public class Game implements Serializable {
    private final int amountStates = 12;
    private final int initilUnts = 10;
    private List<State> states;
    private List<Player> players;

    public Game() {
        initializePlayers();
        initializeStates();    
    }

    public void addPlayer(Player player){
        players.add(player);
        if (players.size() == 2) {
            states.get(0).changeOwner(player, initilUnts);
        }
        if (players.size() == 3) {
            states.get(amountStates - 1).changeOwner(player, initilUnts);
        }
        else{
            //logik für random state bei mehr als 2 echten Spielern  
        }
    }

    private void initializePlayers() {
        players = new ArrayList<>();
        players.add(new Player("", Color.GRAY));
    }
    
    private void initializeStates(){
        states = new ArrayList<>();
        for (int i = 0; i < amountStates; i++) {
            states.add(new State(i, players.get(0), initilUnts));
        }
    }
  
    public List<State> getStates() {
        return states;
    }

    public State getState(int id) {
        return states.get(id);
    }

    public int getStateCount() {
        return states.size();
    }

    public void updateUnits() {
        for (State state : states) {
            if (state.getOwner().getColor() != Color.GRAY || state.getUnits() < initilUnts) {
                //nur bestetze States erhöhen
                state.incrementUnits();
            }
        }
    }

    public void moveUnits(int from, int to) {

        if (states.get(from).getOwner() == states.get(to).getOwner()) {
            states.get(to).setUnits(states.get(to).getUnits() + states.get(from).getUnits());
        }
        else {
            int unitsDiff = states.get(to).getUnits() - states.get(from).getUnits();
            //attacker has more units
            if (unitsDiff < 0) {
                unitsDiff *= -1;
                states.get(to).changeOwner(states.get(from).getOwner(), unitsDiff);
                if (checkWinLoss(states.get(to).getOwner())) {
                    System.out.println(states.get(to).getOwner().getName() + " hat gewonnen");
                    GameController.timer.stop();
                }
            }
            else{
                states.get(to).setUnits(unitsDiff);
            }
        }
        states.get(from).setUnits(0);
    }


    public boolean checkWinLoss(Player player) {
        for (State state : states) {
            if(!state.getOwner().equals(player)) {
                return false;
            }
        }
        return true;
    }
}
