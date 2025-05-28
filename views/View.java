package views;

import javax.swing.JPanel;

import lib.StateManager;

public abstract class View extends JPanel {
    protected Game game;
    protected StateManager stateManager;

    public View(Game game, StateManager stateManager) {
        this.game = game;
        this.stateManager = stateManager;
    }
};