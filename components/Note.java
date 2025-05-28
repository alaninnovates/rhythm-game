package components;

import java.awt.*;

import lib.AssetImage;

public class Note {
    private final NoteType type;
    private final int lane;
    private final int duration;

    private int elapsedDuration = 0;
    private boolean held = false;
    private int y;
    private final AssetImage noteImage = new AssetImage("assets/note.png");

    public Note(NoteType type, int lane, int y, int duration) {
        this.type = type;
        this.lane = lane;
        this.y = y;
        this.duration = duration;
    }

    public NoteType getType() {
        return type;
    }

    private int getX() {
        return switch (lane) {
            case 0 -> 100;
            case 1 -> 200;
            case 2 -> 300;
            case 3 -> 400;
            default -> -1;
        };
    }

    public void draw(Graphics g) {
        if (type == NoteType.Note) {
            // g.fillRect(getX(), y, 50, 50);
            g.drawImage(noteImage.getImage(), getX(), y, 50, 50, null);
        } else if (type == NoteType.HoldNote) {
            g.fillRect(getX(), y-50, 50, duration);
            g.setColor(Color.BLACK);
            g.fillRect(getX(), y-50 + duration - elapsedDuration, 50, elapsedDuration);
            g.setColor(Color.WHITE);
            g.drawImage(noteImage.getImage(), getX(), y, 50, 50, null);
        }
    }

    public void update() {
        this.y += 5;
        if (this.held) {
            System.out.println("Holding note");
            if (type == NoteType.HoldNote && elapsedDuration < duration) {
                this.elapsedDuration += 10;
            }
        }
    }

    public int getY() {
        return y;
    }

    public int getLane() {
        return lane;
    }

    public void setHold(boolean hold) {
        this.held = hold;
    }
}
