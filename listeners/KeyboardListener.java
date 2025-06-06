package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import views.GameView;

public class KeyboardListener implements KeyListener {
    private final GameView gameView;

    public KeyboardListener(GameView gameView) {
        this.gameView = gameView;
        gameView.addKeyListener(this);
        gameView.setFocusable(true);
        gameView.requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_D:
                gameView.triggerLane(0);
                break;
            case KeyEvent.VK_F:
                gameView.triggerLane(1);
                break;
            case KeyEvent.VK_J:
                gameView.triggerLane(2);
                break;
            case KeyEvent.VK_K:
                gameView.triggerLane(3);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//        switch (keyCode) {
//            case KeyEvent.VK_D:
//                gameView.releaseLane(0);
//                break;
//            case KeyEvent.VK_F:
//                gameView.releaseLane(1);
//                break;
//            case KeyEvent.VK_J:
//                gameView.releaseLane(2);
//                break;
//            case KeyEvent.VK_K:
//                gameView.releaseLane(3);
//                break;
//            default:
//                break;
//        }
    }
}
