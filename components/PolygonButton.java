package components;

import java.awt.*;

public class PolygonButton implements Button {
    private final Color color, hoverColor;
    private final Runnable onClickAction;
    private final Polygon polygon;

    private boolean hovered;

    public PolygonButton(int[] xPoints, int[] yPoints, Color color, Color hoverColor, Runnable onClickAction) {
        this.color = color;
        this.hoverColor = hoverColor;
        this.onClickAction = onClickAction;

        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
    }

    public boolean contains(int mouseX, int mouseY) {
        return polygon.contains(mouseX, mouseY);
    }

    public void draw(Graphics g) {
        if (hovered) {
            g.setColor(hoverColor);
        } else {
            g.setColor(color);
        }
        g.fillPolygon(polygon);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void onClick() {
        onClickAction.run();
    }
}
