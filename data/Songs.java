package data;

import java.io.File;
import java.util.ArrayList;

public class Songs {
    private static final String SONGS_DIR = "songs/";

    public static ArrayList<String> getAllSongFiles() {
        ArrayList<String> songs = new ArrayList<>();
        File songsDir = new File(SONGS_DIR);
        if (!songsDir.exists() || !songsDir.isDirectory()) {
            System.out.println("Songs directory does not exist or is not a directory.");
            return songs;
        }
        File[] files = songsDir.listFiles();
        if (files == null) {
            System.out.println("No files found in songs directory.");
            return songs;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".song")) {
                songs.add(file.getName());
            }
        }
        return songs;
    }
}
