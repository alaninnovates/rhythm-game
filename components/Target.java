package components;

import java.awt.Color;
import java.awt.Graphics;

import lib.AssetImage;
import utils.Constants;

public class Target {
    private final int lane;
    private final int x, y;
    private int animateSuccessTime = 0, animatePressTime = 0;
    private boolean isHeld = false;

    public Target(int lane, int x, int y) {
        this.lane = lane;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.decode("#8451C9"));
        if (animateSuccessTime > 0 || animatePressTime > 0 || isHeld) {
            if (animatePressTime > 0 || isHeld) {
                g.setColor(Color.YELLOW);
            }
            if (animateSuccessTime > 0) {
                g.setColor(Color.GREEN);
            }
        }
        g.fillOval(x-30, y-30, 60, 60);
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
