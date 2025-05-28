package components;

import java.awt.Graphics;

public class DisappearingText {
    private String text;
    private int x;
    private int y;
    private int disappearTime;

    public DisappearingText(String text, int x, int y, int disappearTime) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.disappearTime = disappearTime;
    }

    public String getText() {
        return text;
    }

    public void draw(Graphics g) {
        if (disappearTime > 0) {
            g.drawString(text, x, y);
            disappearTime--;
        }
    }

    public boolean isFinished() {
        return disappearTime <= 0;
    }
}
