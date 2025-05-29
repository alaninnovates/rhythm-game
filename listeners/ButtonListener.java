package listeners;

import components.buttons.Button;
import views.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ButtonListener implements MouseMotionListener, MouseListener {
    private final View view;
    private final Button[] buttons;

    public ButtonListener(View homeView, Button[] buttons) {
        this.view = homeView;
        this.buttons = buttons;

        homeView.addMouseMotionListener(this);
        homeView.addMouseListener(this);
        homeView.setFocusable(true);
        homeView.requestFocusInWindow();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (Button button : buttons) {
            if (button.contains(x, y)) {
                button.setHovered(true);
                view.repaint();
            } else {
                button.setHovered(false);
                view.repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (Button button : buttons) {
            if (button.contains(x, y)) {
                button.onClick();
                view.repaint();
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
