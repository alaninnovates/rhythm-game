package lib;

import java.util.Map;

public class StateManager {
    private String chosenSong;
    private int score;
    private final Map<String, Integer> accuracyCounts;
    private int maxCombo, currentCombo;

    public StateManager() {
        this.chosenSong = null;
        this.score = 0;
        this.accuracyCounts = new java.util.HashMap<>();
        this.maxCombo = 0;
        this.currentCombo = 0;
    }

    public String getChosenSong() {
        return chosenSong;
    }

    public void setChosenSong(String chosenSong) {
        this.chosenSong = chosenSong;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore(int increment) {
        this.score += increment;
    }

    public int getAccuracyCount(String accuracy) {
        return accuracyCounts.getOrDefault(accuracy, 0);
    }

    public void incrementAccuracyCount(String accuracy, int count) {
        accuracyCounts.put(accuracy, getAccuracyCount(accuracy) + count);
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public void incrementCurrentCombo() {
        currentCombo++;
        if (currentCombo > maxCombo) {
            maxCombo = currentCombo;
        }
    }

    public void resetCurrentCombo() {
        currentCombo = 0;
    }

    public void retry() {
        this.score = 0;
        this.maxCombo = 0;
        this.currentCombo = 0;
        this.accuracyCounts.clear();
    }

    public void reset() {
        this.chosenSong = null;
        retry();
    }
}
