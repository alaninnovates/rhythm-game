package utils;

import javax.swing.JPanel;

public class Updater implements Runnable {
    private final JPanel panel;

    public Updater(JPanel panel) {
        this.panel = panel;
        Thread repainter = new Thread(this, "RepainterThread");
        repainter.start();
    }

    @Override
    public void run() {
        while (true) {
            panel.repaint();
            try {
                Thread.sleep(1000 / Constants.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}