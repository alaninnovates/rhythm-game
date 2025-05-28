package views;

import components.*;
import data.Song;
import data.SongFileProcessor;
import lib.StateManager;
import listeners.KeyboardListener;
import utils.Updater;

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;

public class GameViewNew extends View {
    private final Song song;
    private final NoteLine[] noteLines = new NoteLine[] {
            new NoteLine(200, 300, 123, 600),
            new NoteLine(250, 300, 221, 600),
            new NoteLine(300, 300, 333, 600), // 304
            new NoteLine(350, 300, 431, 600) // 355
    };

    public GameViewNew(Game game, StateManager stateManager) {
        super(game, stateManager);
        new Updater(this);

        song = SongFileProcessor.processSong("songs/" + stateManager.getChosenSong());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        g.setColor(new Color(40, 103, 184));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(g.getFont().deriveFont(30f));
        g.setColor(Color.WHITE);
        g.drawString("Press D, F, J, K to hit the notes", 50, 50);
        g.drawString("Score: " + stateManager.getScore(), 50, 100);
        g.drawString("Song: " + song.name(), 50, 150);

        for (NoteLine noteLine : noteLines) {
            noteLine.draw(g, -1);
        }
    }
}