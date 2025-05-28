package views;

import components.*;
import data.Song;
import data.SongFileProcessor;
import lib.AssetImage;
import lib.StateManager;
import listeners.KeyboardListener;
import utils.Updater;

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;

public class GameView extends View {
    private final Song song;
    private final NoteLine[] noteLines = new NoteLine[]{
            new NoteLine(200, 300, 123, 600),
            new NoteLine(250, 300, 221, 600),
            new NoteLine(304, 300, 333, 600),
            new NoteLine(355, 300, 431, 600)
    };
    private final Target[] targets = new Target[]{
            new Target(0, 154, 480),
            new Target(1, 233, 480),
            new Target(2, 321, 480),
            new Target(3, 400, 480)
    };
    private final AssetImage backgroundImage = new AssetImage("assets/synthwave.png");
    private int percentCompleted = 0;

    public GameView(Game game, StateManager stateManager) {
        super(game, stateManager);
        new KeyboardListener(this);
        new Updater(this);

        song = SongFileProcessor.processSong("songs/" + stateManager.getChosenSong());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        g.drawImage(backgroundImage.getImage(), 0, 0, null);

        // g.drawString("Press D, F, J, K to hit the notes", 50, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Open Sans", Font.PLAIN, 15));
        g.drawString(song.name(), 18, 26);
        g.setFont(new Font("Open Sans", Font.PLAIN, 11));
        g.drawString(song.artist(), 18, 40);
//        g.drawString("Score: " + stateManager.getScore(), 50, 100);

        // progress bar
        g.setColor(Color.decode("#D1D1D1"));
        g.fillRoundRect(502, 150, 30, 287, 5, 5);
        g.setColor(Color.decode("#8451C9"));
        g.fillRoundRect(502, 150 + 287 - (287 * percentCompleted) / 100, 30, 287 * percentCompleted/100, 5, 5);
        g.setFont(new Font("Open Sans", Font.PLAIN, 13));
        g.setColor(Color.WHITE);
        g.drawString(percentCompleted + "%", 504, 150 + 287 - (287 * percentCompleted) / 100 + 15);

        for (NoteLine noteLine : noteLines) {
            noteLine.draw(g, percentCompleted);
        }

        for (Target target : targets) {
            target.draw(g);
            target.update();
        }
        percentCompleted = Math.min(100, percentCompleted + 1);
    }

    public void triggerLane(int lane) {
        if (lane < 0 || lane >= noteLines.length) return;
        targets[lane].triggerPress();
        repaint();
    }

    public void releaseLane(int lane) {
        if (lane < 0 || lane >= noteLines.length) return;
        targets[lane].triggerRelease();
        repaint();
    }
}