package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<State> states;
    private List<Player> players;
    private Player currentPlayer;

    public Game(Player player) {
        initializePlayers(player);
        currentPlayer = players.get(0);
        initializeStates();    
    }

    private void initializePlayers(Player player) {
        players = new ArrayList<>();
        players.add(new Player("", Color.GRAY));
        players.add(player);
    }
    
    private void initializeStates(){
        states = new ArrayList<>();
        states.add(new State(0, players.get(1), 10));
        for (int i = 1; i < 11; i++) {
            states.add(new State(i, players.get(0), 10));
        }
        if (players.size() > 2) {
            states.add(new State(11, players.get(2), 10));
        }
        else{
            states.add(new State(11, players.get(0), 10));
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
            if (state.getOwner().getColor() != Color.GRAY || state.getUnits() < 10) {
                state.incrementUnits();
            }
        }
    }

    public void moveUnits(int from, int to) {
        State fromState = getState(from);
        State toState = getState(to);
        //State have same owner
        if (fromState.getOwner() == toState.getOwner()) {
            toState.setUnits(toState.getUnits() + fromState.getUnits());
        }
        else{
            int unitsDiff = toState.getUnits() - fromState.getUnits();
            //attacker has more units
            if (unitsDiff < 0) {
                unitsDiff *= -1;
                toState.changeOwner(fromState.getOwner(), unitsDiff);
            }
            //attacker has less units
            else{
                toState.setUnits(unitsDiff);
            }
        }
        fromState.setUnits(0);
    }
    public void moveUnits(State fromState, State toState) {
        //State have same owner
        if (fromState.getOwner() == toState.getOwner()) {
            toState.setUnits(toState.getUnits() + fromState.getUnits());
        }
        else{
            int unitsDiff = toState.getUnits() - fromState.getUnits();
            //attacker has more units
            if (unitsDiff < 0) {
                unitsDiff *= -1;
                toState.changeOwner(fromState.getOwner(), unitsDiff);
            }
            //attacker has less units
            else{
                toState.setUnits(unitsDiff);
            }
        }
        fromState.setUnits(0);
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer() {
        if (currentPlayer == players.get(1) && players.size() > 2) {
            currentPlayer = players.get(2);
        }
        else if (currentPlayer == players.get(2)) {
            currentPlayer = players.get(1);
        }
    }
}
