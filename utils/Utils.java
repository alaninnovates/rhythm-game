package utils;

import java.awt.*;

public class Utils {
    public static int getTextWidth(Graphics g, String text) {
        FontMetrics metrics = g.getFontMetrics();
        return metrics.stringWidth(text);
    }

    public static int computeColumnX(Graphics g, int start, int columnIndex, double space, String text) {
        return (int) (start + space * (columnIndex + 0.5) - (double) getTextWidth(g, text) / 2);
    }

    public static String formatTime(int ms) {
        int seconds = ms / 1000;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
