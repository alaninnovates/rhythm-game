package components.buttons;

import java.awt.*;

public class RectangleButton extends PolygonButton {
    public RectangleButton(int x, int y, int width, int height, Color color, Color hoverColor, Runnable onClickAction) {
        super(new int[]{x, x + width, x + width, x},
                new int[]{y, y, y + height, y + height},
                color, hoverColor, onClickAction);
    }
}
