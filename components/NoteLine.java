package components;

import data.Coordinate;

import java.awt.*;

public class NoteLine {
    private final Coordinate start, end;

    public NoteLine(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(start.x(), start.y(), end.x(), end.y());
    }

    public Coordinate computeNotePosition(double noteTravelPercent) {
        int currentX = (int) (start.x() + (end.x() - start.x()) * noteTravelPercent / 100);
        int currentY = (int) (start.y() + (end.y() - start.y()) * noteTravelPercent / 100);
        return new Coordinate(currentX, currentY);
    }
}
