package components.buttons;

import java.awt.*;
import java.util.function.BooleanSupplier;

public class PolygonButton implements Button {
    private final Color color, hoverColor;
    private final Runnable onClickAction;
    private final Polygon polygon;
    private final BooleanSupplier visibleSupplier;

    private boolean hovered;

    public PolygonButton(int[] xPoints, int[] yPoints, Color color, Color hoverColor, Runnable onClickAction, BooleanSupplier visibleSupplier) {
        this.color = color;
        this.hoverColor = hoverColor;
        this.onClickAction = onClickAction;
        this.visibleSupplier = visibleSupplier;

        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
    }

    public PolygonButton(int[] xPoints, int[] yPoints, Color color, Color hoverColor, Runnable onClickAction) {
        this(xPoints, yPoints, color, hoverColor, onClickAction, () -> true);
    }

    public boolean contains(int mouseX, int mouseY) {
        return polygon.contains(mouseX, mouseY);
    }

    public void draw(Graphics g) {
        if (!visibleSupplier.getAsBoolean()) {
            return;
        }
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
