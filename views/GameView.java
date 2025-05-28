package views;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Timer;

import components.Note;
import components.NoteType;
import components.DisappearingText;
import components.Target;
import data.NoteData;
import data.Song;
import listeners.KeyboardListener;
import data.SongFileProcessor;
import lib.StateManager;
import utils.Updater;

public class GameView extends View {
    private final Song song;
    private final LinkedList<Note> notes = new LinkedList<>();
    private final Target[] targets = new Target[] { new Target(0), new Target(1), new Target(2), new Target(3) };
    private DisappearingText disappearingText;
    private boolean gameOverTimerStarted = false;

    public GameView(Game game, StateManager stateManager) {
        super(game, stateManager);
        new KeyboardListener(this);
        new Updater(this);

        song = SongFileProcessor.processSong("songs/" + stateManager.getChosenSong());
        notes.addAll(song.notes());
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        g.setColor(new Color(40, 103, 184));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(g.getFont().deriveFont(30f));
        g.setColor(Color.WHITE);
        g.drawString("Press D, F, J, K to hit the notes", 50, 50);
        g.drawString("Score: " + stateManager.getScore(), 50, 100);
        g.drawString("Song: " + song.name(), 50, 150);

        for (Note note : notes) {
            note.draw(g);
        }

        if (disappearingText != null) {
            disappearingText.draw(g);
            if (disappearingText.isFinished()) {
                disappearingText = null;
            }
        }

        drawLaneTargets(g);
        updateGame();
    }

    public void updateGame() {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            note.update();
            if (note.getY() > getHeight()) {
                notes.remove(note);
            }
        }

        if (notes.isEmpty() && !gameOverTimerStarted) {
            gameOverTimerStarted = true;
            new Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    game.showEndView();
                }
            }, 1000);
        }
    }

    public void drawLaneTargets(Graphics g) {
        for (Target target : targets) {
            target.draw(g);
            target.update();
        }
    }

    public void triggerLane(int lane) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getType() == NoteType.Note) {
                int dist = distanceToTarget(note);
                if (note.getLane() == lane && dist < 50) {
                    notes.remove(note);
                    targets[lane].triggerSuccess();
                    disappearingText = new DisappearingText(getAnimateText(dist), getWidth() / 2, getHeight() / 2, 20);
                    stateManager.incrementScore(getScore(dist));
                }
            } else if (note.getType() == NoteType.HoldNote) {
                if (note.getLane() == lane) {
                    note.setHold(true);
                    targets[lane].triggerHold();
                }
            }
        }
        targets[lane].triggerPress();
        repaint();
    }

    public void releaseLane(int lane) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getType() == NoteType.HoldNote) {
                if (note.getLane() == lane) {
                    note.setHold(false);
                    targets[lane].triggerRelease();
//                    stateManager.incrementScore();
                }
            }
        }
        repaint();
    }

    public int distanceToTarget(Note note) {
        return Math.abs(note.getY() - (getHeight() - 100));
    }

    public String getAnimateText(int dist) {
        if (dist < 5) {
            return "Perfect!";
        } else if (dist < 20) {
            return "Great";
        } else {
            return "Good";
        }
    }

    public int getScore(int dist) {
        if (dist < 5) {
            return 100;
        } else if (dist < 20) {
            return 50;
        } else {
            return 10;
        }
    }
}