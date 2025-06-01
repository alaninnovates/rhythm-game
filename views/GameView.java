package views;

import components.*;
import data.Coordinate;
import data.NoteData;
import data.Song;
import data.SongFileProcessor;
import lib.AssetImage;
import lib.StateManager;
import listeners.KeyboardListener;
import utils.Updater;

import java.awt.*;
import java.util.LinkedList;

public class GameView extends View {
    private final Song song;
    private final LinkedList<Note> notes;
    private final NoteLine[] noteLines = new NoteLine[]{
            new NoteLine(new Coordinate(200, 300), new Coordinate(123, 600)),
            new NoteLine(new Coordinate(250, 300), new Coordinate(221, 600)),
            new NoteLine(new Coordinate(304, 300), new Coordinate(333, 600)),
            new NoteLine(new Coordinate(355, 300), new Coordinate(431, 600))
    };
    private final int targetPercent = 60;
    private final Target[] targets = new Target[]{
            new Target(0, noteLines[0].computeNotePosition(targetPercent).x(), noteLines[0].computeNotePosition(targetPercent).y()),
            new Target(1, noteLines[1].computeNotePosition(targetPercent).x(), noteLines[1].computeNotePosition(targetPercent).y()),
            new Target(2, noteLines[2].computeNotePosition(targetPercent).x(), noteLines[2].computeNotePosition(targetPercent).y()),
            new Target(3, noteLines[3].computeNotePosition(targetPercent).x(), noteLines[3].computeNotePosition(targetPercent).y())
    };
    private final AssetImage backgroundImage = new AssetImage("assets/synthwave.png");
    private final long startMs;
    private DisappearingText disappearingText;
    private int percentCompleted = 0;

    public GameView(Game game, StateManager stateManager) {
        super(game, stateManager);
        new KeyboardListener(this);
        new Updater(this);

        song = SongFileProcessor.processSong("songs/" + stateManager.getChosenSong());
        notes = new LinkedList<>();
        for (NoteData data : song.notes()) {
            Note note = new Note(data);
            notes.add(note);
        }

        startMs = System.currentTimeMillis();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        g.drawImage(backgroundImage.getImage(), 0, 0, null);

        // g.drawString("Press D, F, J, K to hit the notes", 50, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Open Sans", Font.PLAIN, 15));
        g.drawString(song.metadata().name(), 18, 26);
        g.setFont(new Font("Open Sans", Font.PLAIN, 11));
        g.drawString(song.metadata().artist(), 18, 40);
        g.drawString("Score: " + stateManager.getScore(), 18, 54);

        // progress bar
        g.setColor(Color.decode("#D1D1D1"));
        g.fillRoundRect(502, 150, 30, 287, 5, 5);
        g.setColor(Color.decode("#8451C9"));
        int minPC = Math.min(100, percentCompleted);
        g.fillRoundRect(502, 150 + 287 - (287 * minPC) / 100, 30, 287 * minPC / 100, 5, 5);
        g.setFont(new Font("Open Sans", Font.PLAIN, 13));
        g.setColor(Color.WHITE);
        g.drawString(minPC + "%", 504, 150 + 287 - (287 * minPC) / 100 + 15);

        for (NoteLine noteLine : noteLines) {
            noteLine.draw(g);
        }

        for (Target target : targets) {
            target.draw(g);
            target.update();
        }

        g.setColor(Color.WHITE);
        for (Note note : notes) {
            double travelPercent = computeTravelPercent(note);
            if (travelPercent < 0) continue;
            Coordinate current = noteLines[note.getData().lane()].computeNotePosition(travelPercent);
            g.fillOval(current.x() - 30, current.y() - 30, 60, 60);
        }

        if (disappearingText != null) {
            disappearingText.draw(g);
            if (disappearingText.isFinished()) {
                disappearingText = null;
            }
        }

        percentCompleted = (int) ((1-((double)(song.metadata().duration() - (System.currentTimeMillis() - startMs)) / song.metadata().duration())) * 100);

        if (percentCompleted >= 130) {
            game.showEndView();
        }
    }

    public void triggerLane(int lane) {
        if (lane < 0 || lane >= noteLines.length) return;
        targets[lane].triggerPress();
        for (Note note : notes) {
            if (note.getData().lane() == lane) {
                double travelPercent = computeTravelPercent(note);
                System.out.println(travelPercent);
                if (travelPercent - targetPercent > 30) {
                    targets[lane].triggerMiss();
                    disappearingText = new DisappearingText("Miss", getWidth() / 2, getHeight() / 2, 20);
                    stateManager.incrementAccuracyCount("Miss", 1);
                    continue;
                }
                if (note.getData().type() == NoteType.HoldNote) {
                    targets[lane].triggerHold();
                } else {
                    targets[lane].triggerSuccess();
                    notes.remove(note);
                    String stateText = getAnimateText(Math.abs(travelPercent - targetPercent));
                    disappearingText = new DisappearingText(stateText + "!", getWidth() / 2, getHeight() / 2, 20);
                    stateManager.incrementAccuracyCount(stateText, 1);
                    int score = computeScore(travelPercent);
                    stateManager.incrementScore(score);
                }
                repaint();
                return;
            }
        }
        repaint();
    }

    public void releaseLane(int lane) {
        if (lane < 0 || lane >= noteLines.length) return;
        targets[lane].triggerRelease();
        repaint();
    }

    private double computeTravelPercent(Note note) {
        long elapsed = System.currentTimeMillis() - startMs;
        if (note.getData().startTime() > elapsed) {
            return -1; // note hasn't started yet
        }
        double trackLengthMs = (double) (60000 / song.metadata().bpm()) * 5; // 5 seconds of track
        return (elapsed - note.getData().startTime()) / trackLengthMs * 100;
    }

    private int computeScore(double travelPercent) {
        if (travelPercent < 0 || travelPercent > 100) return 0;
        return (int) Math.max(0, 100 - Math.abs(targetPercent - travelPercent));
    }

    private String getAnimateText(double dist) {
        if (dist < 10) return "Perfect";
        if (dist < 20) return "Great";
        if (dist < 30) return "Good";
        return "Okay";
    }
}