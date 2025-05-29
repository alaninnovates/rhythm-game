package utils;

import java.awt.*;

public class Utils {
    public static int getTextLength(Graphics g, String text) {
        FontMetrics metrics = g.getFontMetrics();
        return metrics.stringWidth(text);
    }

    public static int computeColumnX(Graphics g, int start, int columnIndex, double space, String text) {
        return (int) (start + space * (columnIndex + 0.5) - (double) getTextLength(g, text) / 2);
    }
}
