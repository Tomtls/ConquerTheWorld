package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<State> states;

    public Game(Player player1, Player player2) {
        initializeStates(player1, player2);
    }
    
    private void initializeStates(Player player1, Player player2){
        states = new ArrayList<>();
        states.add(new State(0, player1.getColor(), 10));
        for (int i = 1; i < 11; i++) {
            states.add(new State(i, Color.GRAY, 10));
        }
        states.add(new State(11, player2.getColor(), 10));
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

    public void updateStates() {
        for (State state : states) {
            if (state.getColor() != Color.GRAY || state.getUnits() < 10) {
                state.incrementUnits();
            }
        }
    }
}
