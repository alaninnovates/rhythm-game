package views;

import components.Button;
import components.RectangleButton;
import lib.StateManager;
import listeners.ButtonListener;

import java.awt.*;

public class EndView extends View {
    RectangleButton homeButton, playAgainButton;

    public EndView(Game game, StateManager stateManager) {
        super(game, stateManager);

        playAgainButton = new RectangleButton(291, 435, 152, 51, Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"), () -> {
            game.showGameView();
        });
        homeButton = new RectangleButton(106, 435, 152, 51, Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"), () -> {
            stateManager.setChosenSong(null);
            game.showHomeView();
        });
        new ButtonListener(this, new Button[]{homeButton, playAgainButton});
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.decode("#6823C3"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.decode("#8451C9"));
        g.fillRoundRect(59, 89, 431, 422, 12, 12);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Open Sans", Font.BOLD, 36));
        g.drawString("Song Complete!", 114, 175);

        g.setFont(new Font("Open Sans", Font.PLAIN, 20));
        homeButton.draw(g);
        playAgainButton.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("Home", 153, 435 + 32);
        g.drawString("Play Again", 319, 435 + 32);
    }
}
