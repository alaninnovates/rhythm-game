package components;

import java.awt.*;

public interface Button {
    boolean contains(int mouseX, int mouseY);
    void draw(Graphics g);
    void setHovered(boolean hovered);
    void onClick();
}
