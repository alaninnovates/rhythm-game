package views;

import components.*;
import data.Coordinate;
import data.NoteData;
import data.Song;
import data.SongFileProcessor;
import lib.AssetImage;
import lib.MediaLoader;
import lib.StateManager;
import listeners.KeyboardListener;
import utils.Updater;
import utils.Utils;

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

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
    private final AssetImage partyPopper = new AssetImage("assets/party-popper.png");
    private final long startMs;
    private final Updater updater;
    private final MediaLoader mediaLoader;

    private DisappearingText disappearingText;
    private int percentCompleted = 0;

    public GameView(Game game, StateManager stateManager) {
        super(game, stateManager);
        new KeyboardListener(this);
        updater = new Updater(this);
        updater.start();

        song = SongFileProcessor.processSong("songs/" + stateManager.getChosenSong());
        notes = new LinkedList<>();
        for (NoteData data : song.notes()) {
            Note note = new Note(data);
            notes.add(note);
        }

        try {
            mediaLoader = new MediaLoader("songs/audio/" + stateManager.getChosenSong().replace(".song", ".wav"));
            mediaLoader.play();
        } catch (Exception e) {
            throw new RuntimeException("Error loading song audio: " + e.getMessage(), e);
        }

        // "vacuum" the notes that are already past the target percent
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < notes.size(); i++) {
                    Note note = notes.get(i);
                    double travelPercent = computeTravelPercent(note);
                    if (travelPercent > 100) {
                        stateManager.incrementAccuracyCount("Miss", 1);
                        notes.remove(note);
                        stateManager.resetCurrentCombo();
                    }
                }
            }
        }, 0, 100);


        startMs = System.currentTimeMillis();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        g.drawImage(backgroundImage.getImage(), 0, 0, null);

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
            g.fillOval(current.x() - 20, current.y() - 20, 40, 40);
        }

        if (disappearingText != null) {
            disappearingText.draw(g);
            if (disappearingText.isFinished()) {
                disappearingText = null;
            }
        }

//        percentCompleted = (int) ((1 - ((double) (song.metadata().duration() - (System.currentTimeMillis() - startMs)) / song.metadata().duration())) * 100);
        percentCompleted = 100 - (int) (notes.size() / (double) song.notes().size() * 100);

        if (percentCompleted == 100) {
            updater.stop();
            g.setColor(Color.decode("#6823C3"));
            g.fillRoundRect(89, 165, 371, 257, 12, 12);
            g.drawImage(partyPopper.getImage(), 313, 222, 128, 128, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Open Sans", Font.BOLD, 36));
            g.drawString("Song Complete!", getWidth() / 2 - Utils.getTextWidth(g, "Song Complete!") / 2, 200);
            g.setFont(new Font("Open Sans", Font.PLAIN, 20));
            g.drawString("Congratulations!", 100, 250);
            g.drawString("Scores are loading up...", 100, 310);
            repaint();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    game.showEndView();
                }
            }, 1500);
        }
    }

    public void triggerLane(int lane) {
        if (lane < 0 || lane >= noteLines.length) return;
        targets[lane].triggerPress();
        for (Note note : notes) {
            if (note.getData().lane() == lane) {
                double travelPercent = computeTravelPercent(note);
                if (travelPercent < 0 || travelPercent + 10 < targetPercent || travelPercent > 100) {
                    continue;
                }
                if (travelPercent > targetPercent + 10) {
                    targets[lane].triggerMiss();
                    disappearingText = new DisappearingText("Miss", getWidth() / 2, getHeight() / 2, 20);
                    stateManager.incrementAccuracyCount("Miss", 1);
                    stateManager.resetCurrentCombo();
                    continue;
                }
                targets[lane].triggerSuccess();
                notes.remove(note);
                String stateText = getAnimateText(Math.abs(travelPercent - targetPercent));
                disappearingText = new DisappearingText(stateText + "!", getWidth() / 2, getHeight() / 2, 20);
                stateManager.incrementAccuracyCount(stateText, 1);
                int score = computeScore(travelPercent);
                stateManager.incrementScore(score);
                stateManager.incrementCurrentCombo();
                repaint();
                return;
            }
        }
        repaint();
    }

//    public void releaseLane(int lane) {
//        if (lane < 0 || lane >= noteLines.length) return;
//        repaint();
//    }

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
        if (dist < 3) return "Perfect";
        if (dist < 5) return "Great";
        if (dist < 10) return "Good";
        return "Okay";
    }
}