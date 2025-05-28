package components;

import java.awt.Color;
import java.awt.Graphics;

import lib.AssetImage;
import utils.Constants;

public class Target {
    private final int lane;
    private int animateSuccessTime = 0, animatePressTime = 0;
    private boolean isHeld = false;
    private final AssetImage targetImage = new AssetImage("assets/target.png");

    public Target(int lane) {
        this.lane = lane;
    }

    public void draw(Graphics g) {
        g.drawImage(targetImage.getImage(), 100 + lane * 100 - 20, Constants.SCREEN_HEIGHT - 100, 90, 60, null);
        g.setColor(Color.BLUE);
        if (animateSuccessTime > 0 || animatePressTime > 0 || isHeld) {
            if (animatePressTime > 0 || isHeld) {
                g.setColor(Color.YELLOW);
            }
            if (animateSuccessTime > 0) {
                g.setColor(Color.GREEN);
            }
            g.fillOval(100 + lane * 100 - 20, Constants.SCREEN_HEIGHT - 100, 90, 40);
        }
    }

    public void update() {
        if (animateSuccessTime > 0) {
            animateSuccessTime--;
        }
        if (animatePressTime > 0) {
            animatePressTime--;
        }
    }

    public void triggerSuccess() {
        animateSuccessTime = 5;
    }

    public void triggerPress() {
        animatePressTime = 5;
    }

    public void triggerHold() {
        isHeld = true;
    }

    public void triggerRelease() {
        isHeld = false;
    }
}
