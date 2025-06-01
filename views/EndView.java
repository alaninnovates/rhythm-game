package views;

import components.buttons.Button;
import components.buttons.RectangleButton;
import lib.AssetImage;
import lib.StateManager;
import listeners.ButtonListener;
import utils.Utils;

import java.awt.*;

public class EndView extends View {
    private final RectangleButton homeButton, playAgainButton;
    private final AssetImage djDisk = new AssetImage("assets/dj-disk.png");

    public EndView(Game game, StateManager stateManager) {
        super(game, stateManager);

        playAgainButton = new RectangleButton(291, 435, 152, 51, Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"), () -> {
            game.showGameView();
        });
        homeButton = new RectangleButton(106, 435, 152, 51, Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"), () -> {
            stateManager.retry();
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
        g.drawString("Song Complete!", getWidth() / 2 - Utils.getTextLength(g, "Song Complete!") / 2, 150);
        g.setFont(new Font("Open Sans", Font.PLAIN, 20));
        homeButton.draw(g);
        playAgainButton.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("Home", 153, 435 + 32);
        g.drawString("Play Again", 319, 435 + 32);

        double space = (double) 431 / 3;
        g.setFont(new Font("Open Sans", Font.BOLD, 15));
        g.setColor(Color.decode("#FFE987"));
        g.drawString("Score", Utils.computeColumnX(g, 59, 0, space, "Score"), 195);
        g.drawString("Max Combo", Utils.computeColumnX(g, 59, 1, space, "Max Combo"), 195);
        g.drawString("Rating", Utils.computeColumnX(g, 59, 2, space, "Rating"), 195);

        g.setFont(new Font("Open Sans", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(stateManager.getScore()), Utils.computeColumnX(g, 59, 0, space, "1800"), 225);
        g.drawString(String.valueOf(stateManager.getMaxCombo()), Utils.computeColumnX(g, 59, 1, space, "20"), 225);
        g.drawString(getGrade(), Utils.computeColumnX(g, 59, 2, space, "F"), 225);

        space = (double) 431 / 2;
        // todo: calculate exactly
        int centerAxis = (int) (59 + space * 0.5) + 20;
        int gap = 10;
        g.setColor(Color.decode("#3CEBFF"));
        g.drawString("Perfect", centerAxis - Utils.getTextLength(g, "Perfect") - gap / 2, 270);
        g.setColor(Color.decode("#3CFF4C"));
        g.drawString("Great", centerAxis - Utils.getTextLength(g, "Great") - gap / 2, 300);
        g.setColor(Color.decode("#FFE987"));
        g.drawString("Okay", centerAxis - Utils.getTextLength(g, "Okay") - gap / 2, 330);
        g.setColor(Color.decode("#FF6D6D"));
        g.drawString("Miss", centerAxis - Utils.getTextLength(g, "Miss") - gap / 2, 360);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(stateManager.getAccuracyCount("Perfect")), centerAxis + gap / 2, 270);
        g.drawString(String.valueOf(stateManager.getAccuracyCount("Great")), centerAxis + gap / 2, 300);
        g.drawString(String.valueOf(stateManager.getAccuracyCount("Okay")), centerAxis + gap / 2, 330);
        g.drawString(String.valueOf(stateManager.getAccuracyCount("Miss")), centerAxis + gap / 2, 360);

        g.drawImage(djDisk.getImage(), (int) (59 + space * 1.5) - 108 / 2, 250, 108, 108, null);
    }

    private String getGrade() {
        int perfectCount = stateManager.getAccuracyCount("Perfect");
        int greatCount = stateManager.getAccuracyCount("Great");
        int okayCount = stateManager.getAccuracyCount("Okay");
        int totalCount = perfectCount + greatCount + okayCount + stateManager.getAccuracyCount("Miss");
        if (totalCount == 0) {
            return "F";
        }
        double accuracy = (double) (perfectCount * 2 + greatCount + okayCount) / totalCount;
        if (accuracy >= 0.9) {
            return "S";
        } else if (accuracy >= 0.8) {
            return "A";
        } else if (accuracy >= 0.7) {
            return "B";
        } else if (accuracy >= 0.6) {
            return "C";
        } else if (accuracy >= 0.5) {
            return "D";
        } else {
            return "F";
        }
    }
}
