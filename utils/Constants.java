package utils;

public class Constants {
    public static final int SCREEN_WIDTH = 550;
    public static final int SCREEN_HEIGHT = 600;

    public static final int LANE_COUNT = 4;
    public static final int LANE_WIDTH = SCREEN_WIDTH / LANE_COUNT;

    public static final int BLOCK_WIDTH = LANE_WIDTH;
    public static final int BLOCK_HEIGHT = 50;

    public static final int TARGET_WIDTH = BLOCK_WIDTH;
    public static final int TARGET_HEIGHT = BLOCK_HEIGHT;

    public static final int FPS = 60;
    public static final long FRAME_TIME_MS = 1000 / FPS;
}
