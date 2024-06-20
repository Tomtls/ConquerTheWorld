package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;

public class Game implements Serializable {
    private final int amountStates = 12;
    private final int initilUnts = 10;
    private State [] states;
    private Player [] players = new Player[2];
    private Player nullPlayer = new Player("null", Color.GRAY);
    private Player winner;
    private boolean multiplayer, isOver = false;

    public Game(Player[] players) {
        this.players = players; 
        this.multiplayer = true;
        initializeGame();
    }
    public Game(Player player) {
        this.players[0] = player;
        this.multiplayer = false;
        initializeGame();

    }
    private void initializeGame() {
        states = new State[amountStates];
        states[0] = new State(0, players[0], initilUnts);
        
        for (int i = 1; i < amountStates - 1; i++) {
            states[i] = new State(i, nullPlayer, initilUnts);
        }
        if (multiplayer) {
            states[amountStates - 1] = new State(amountStates - 1, players[1], initilUnts);
        }
        else { states[amountStates -1 ] = new State(amountStates - 1, nullPlayer, initilUnts); }
    }
  

    public void updateUnits() { 
        for (State state : states) {
            if (state.getOwner().getColor() != Color.GRAY || state.getUnits() < initilUnts) {
                state.incrementUnits();
            }
        }
    }

    public void moveUnits(int from, int to) {
        if (states[from].getOwner() == states[to].getOwner()) {
            states[to].setUnits(states[to].getUnits() + states[from].getUnits());
        }
        else {
            int unitsDiff = states[to].getUnits() - states[from].getUnits();
            if (unitsDiff < 0) {
                unitsDiff *= -1;
                states[to].changeOwner(states[from].getOwner(), unitsDiff);
                if (checkWinLoss(states[to].getOwner())) {
                    isOver = true;
                    winner = states[to].getOwner();
                    System.out.println(states[to].getOwner().getName() + " hat gewonnen");
                }
            }
            else{ states[to].setUnits(unitsDiff); }
        }
        states[from].setUnits(0);
    }

    public State getState(int id) { return states[id]; }

    public int getStateCount() { return states.length; }

    public boolean isOver() { return isOver; }

    public Player getWinner() { return winner; }

    public boolean checkWinLoss(Player player) {
        return Arrays.stream(states).allMatch(state -> state.getOwner() == player);
    }
}
