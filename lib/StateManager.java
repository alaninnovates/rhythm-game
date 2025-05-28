package lib;

public class StateManager {
    private String chosenSong;
    private int score;

    public StateManager() {
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

    public void retry() {
        this.score = 0;
    }

    public void reset() {
        this.chosenSong = null;
        this.score = 0;
    }
}
