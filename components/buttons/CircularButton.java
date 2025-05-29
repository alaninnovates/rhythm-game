package components.buttons;

import java.awt.*;

public class CircularButton implements Button {
    private final int x, y, width, height;
    private final Color color, hoverColor;
    private final Runnable onClickAction;

    private boolean hovered;

    public CircularButton(int x, int y, int width, int height, Color color, Color hoverColor, Runnable onClickAction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hoverColor = hoverColor;
        this.onClickAction = onClickAction;
    }

    public boolean contains(int mouseX, int mouseY) {
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        int radius = width / 2;

        return Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2) <= Math.pow(radius, 2);
    }

    public void draw(Graphics g) {
        if (hovered) {
            g.setColor(hoverColor);
        } else {
            g.setColor(color);
        }
        g.fillOval(x, y, width, height);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void onClick() {
        onClickAction.run();
    }
}
