package lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class SaveManager {
    private LinkedList<String> bookmarkedSongs;

    public SaveManager() {
        loadData();
    }

    public void bookmarkSong(String songName) {
        if (!bookmarkedSongs.contains(songName)) {
            bookmarkedSongs.add(songName);
            writeData();
        }
    }

    public void unbookmarkSong(String songName) {
        bookmarkedSongs.remove(songName);
        writeData();
    }

    public boolean isBookmarked(String songName) {
        return bookmarkedSongs.contains(songName);
    }

    private void loadData() {
        try {
            Path path = Paths.get("data/bookmarked_songs.txt");
            if (Files.exists(path)) {
                bookmarkedSongs = new LinkedList<>(Files.readAllLines(path));
            } else {
                bookmarkedSongs = new LinkedList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            bookmarkedSongs = new LinkedList<>();
        }
    }

    private void writeData() {
        try {
            Path path = Paths.get("data/bookmarked_songs.txt");
            Files.write(path, bookmarkedSongs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
