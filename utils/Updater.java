package utils;

import javax.swing.*;

public class Updater {
    private final Timer timer;

    public Updater(JPanel panel) {
        timer = new Timer((int) Constants.FRAME_TIME_MS, e -> {
            panel.repaint();
            panel.revalidate();
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}