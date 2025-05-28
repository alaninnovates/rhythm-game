package views;

import components.Button;
import components.CircularButton;
import components.PolygonButton;
import data.Difficulty;
import data.Song;
import data.SongFileProcessor;
import data.Songs;
import lib.StateManager;
import listeners.ButtonListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeView extends View {
    private final CircularButton playButton, bookmarkButton;
    private final PolygonButton previousButton, nextButton;
    private final ArrayList<Song> songs = new ArrayList<>();
    private final HashMap<String, String> songFileMap = new HashMap<>();
    private final HashMap<Difficulty, Color> difficultyColors = new HashMap<>() {{
        put(Difficulty.Easy, Color.decode("#6FFFA0"));
        put(Difficulty.Medium, Color.decode("#FFB86F"));
        put(Difficulty.Hard, Color.decode("#FF6F6F"));
    }};
    private Song currentSong;

    public HomeView(Game game, StateManager stateManager) {
        super(game, stateManager);
        setLayout(new BorderLayout());

        for (String song : Songs.getAllSongFiles()) {
            Song s = SongFileProcessor.processSong("songs/" + song);
            songs.add(s);
            songFileMap.put(s.name(), song);
        }
        currentSong = songs.get(0);

        playButton = new CircularButton(460, 234, 60, 60, Color.decode("#56C45F"),
                Color.decode("#A0E6A0"), () -> {
            stateManager.setChosenSong(songFileMap.get(currentSong.name()));
            game.showGameView();
        });
        bookmarkButton = new CircularButton(460, 300, 60, 60, Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"), () -> {

        });

        previousButton = new PolygonButton(
                new int[]{275, 301, 250},
                new int[]{122, 167, 167},
                Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"),
                () -> {
                    int currentIndex = songs.indexOf(currentSong);
                    if (currentIndex > 0) {
                        currentSong = songs.get(currentIndex - 1);
                        repaint();
                    }
                }
        );
        nextButton = new PolygonButton(
                new int[]{275, 301, 250},
                new int[]{477, 432, 432},
                Color.decode("#A07ECE"),
                Color.decode("#D1C0E6"),
                () -> {
                    int currentIndex = songs.indexOf(currentSong);
                    if (currentIndex < songs.size() - 1) {
                        currentSong = songs.get(currentIndex + 1);
                        repaint();
                    }
                }
        );
        new ButtonListener(this, new Button[]{playButton, bookmarkButton, previousButton, nextButton});

        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.decode("#6823C3"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.decode("#8451C9"));
        g.fillRoundRect(59, 152, 431, 295, 12, 12);

        // play button
        playButton.draw(g);
        g.setColor(Color.BLACK);
        g.fillPolygon(new int[]{484, 500, 484}, new int[]{253, 263, 274}, 3);

        // bookmark button
        bookmarkButton.draw(g);
        g.setColor(Color.BLACK);
        g.fillPolygon(new int[]{482, 482, 498, 498, 490}, new int[]{340, 319, 319, 340, 336}, 5);

        previousButton.draw(g);
        nextButton.draw(g);

        g.setFont(new Font("Yellowtail", Font.PLAIN, 48));
        g.setColor(Color.WHITE);
        g.drawString(currentSong.name(), 100, 210);
        g.setFont(new Font("Open Sans", Font.PLAIN, 15));
        g.drawString(currentSong.artist(), 110, 240);

        g.setColor(difficultyColors.get(currentSong.difficulty()));
        g.fillRoundRect(109, 263, 82, 22, 10, 10);
        g.setColor(Color.WHITE);
        int len = g.getFontMetrics().stringWidth(currentSong.difficulty().toString());
        g.drawString(currentSong.difficulty().toString(), 109 + (82 - len) / 2, 280);

        g.drawString("BPM: " + currentSong.bpm(), 110, 310);
    }
}
