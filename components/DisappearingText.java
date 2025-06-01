package components;

import utils.Utils;

import java.awt.*;

public class DisappearingText {
    private final String text;
    private final int x, y;
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
            g.setFont(new Font("Open Sans", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString(text, x - Utils.getTextLength(g, text) / 2, y);
            disappearTime--;
        }
    }

    public boolean isFinished() {
        return disappearTime <= 0;
    }
}
