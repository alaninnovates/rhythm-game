package components;

import java.awt.*;

public class NoteLine {
    private final int startX, startY, endX, endY;

    public NoteLine(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void draw(Graphics g, int noteTravelPercent) {
        int currentX = startX + (endX - startX) * noteTravelPercent / 100;
        int currentY = startY + (endY - startY) * noteTravelPercent / 100;
        g.setColor(Color.WHITE);
        g.drawLine(startX, startY, endX, endY);
        if (noteTravelPercent >= 0) {
            g.drawOval(currentX, currentY, 20, 20);
        }
    }
}
