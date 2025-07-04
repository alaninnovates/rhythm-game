package components;

import java.awt.Color;
import java.awt.Graphics;

import lib.AssetImage;
import utils.Constants;
import utils.Utils;

public class Target {
    private final int lane;
    private final int x, y;
    private int animateSuccessTime = 0, animatePressTime = 0, animateMissTime = 0;
    private boolean isHeld = false;

    public Target(int lane, int x, int y) {
        this.lane = lane;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.decode("#8451C9"));
        if (animateSuccessTime > 0 || animatePressTime > 0 || animateMissTime > 0 || isHeld) {
            if (animatePressTime > 0 || isHeld) {
                g.setColor(Color.YELLOW);
            }
            if (animateSuccessTime > 0) {
                g.setColor(Color.GREEN);
            }
            if (animateMissTime > 0) {
                g.setColor(Color.RED);
            }
        }
        g.fillOval(x - 30, y - 30, 60, 60);
        g.setColor(Color.WHITE);
        String laneKey = getLaneKey();
        g.drawString(laneKey, x - Utils.getTextWidth(g, laneKey) / 2, y + 5);
    }

    public void update() {
        if (animateSuccessTime > 0) {
            animateSuccessTime--;
        }
        if (animatePressTime > 0) {
            animatePressTime--;
        }
        if (animateMissTime > 0) {
            animateMissTime--;
        }
    }

    public void triggerSuccess() {
        animateSuccessTime = 5;
    }

    public void triggerPress() {
        animatePressTime = 5;
    }

    public void triggerMiss() {
        animateMissTime = 5;
    }

//    public void triggerHold() {
//        isHeld = true;
//    }

//    public void triggerRelease() {
//        isHeld = false;
//    }

    private String getLaneKey() {
        return switch (lane) {
            case 0 -> "D";
            case 1 -> "F";
            case 2 -> "J";
            case 3 -> "K";
            default -> throw new IllegalStateException("Unexpected value: " + lane);
        };
    }
}
