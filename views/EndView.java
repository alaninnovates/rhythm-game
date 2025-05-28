package views;

import lib.StateManager;

import javax.swing.*;
import java.awt.*;

public class EndView extends View {

    public EndView(Game game, StateManager stateManager) {
        super(game, stateManager);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("Your Score: " + stateManager.getScore());
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retryButton = new JButton("Retry");
        JButton menuButton = new JButton("Return to Menu");

        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        retryButton.addActionListener(e -> {
            stateManager.reset();
            game.showGameView();
        });

        menuButton.addActionListener(e -> {
            game.showHomeView();
        });

        // box layout is annoying
        add(gameOverLabel);
        // spacing/padding:
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(retryButton);
        add(menuButton);
    }
}
