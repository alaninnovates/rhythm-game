package views;

import javax.swing.JFrame;

import lib.FontLoader;
import utils.Constants;
import lib.StateManager;

public class Game {
    private final JFrame frame;
    private final StateManager stateManager;

    public Game() {
        frame = new JFrame("RhythmGame");
        stateManager = new StateManager();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {
        frame.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        FontLoader.loadFonts();

        showHomeView();
    }

    public void showHomeView() {
        showView(new HomeView(this, stateManager));
    }

    public void showGameView() {
        showView(new GameView(this, stateManager));
    }

    public void showEndView() {
        showView(new EndView(this, stateManager));
    }

    public void showView(View v) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(v);
        frame.revalidate();
        frame.repaint();
    }
}
