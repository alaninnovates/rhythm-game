package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import components.NoteType;

public class SongFileProcessor {
    private static String readFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }

    private static ArrayList<ArrayList<String>> splitLines(String fileContent) {
        ArrayList<String> meta = new ArrayList<>();
        ArrayList<String> notes = new ArrayList<>();
        boolean noteSection = false;

        for (String line : fileContent.split("\n")) {
            if (line.trim().equals("::")) {
                noteSection = true;
                continue;
            }
            if (noteSection) {
                notes.add(line);
            } else {
                meta.add(line);
            }
        }

        ArrayList<ArrayList<String>> ret = new ArrayList<>();
        ret.add(meta);
        ret.add(notes);
        return ret;
    }

    public static Song processSong(String filePath) {
        String fileContent;
        try {
            fileContent = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }

        ArrayList<ArrayList<String>> lines = splitLines(fileContent);

        ArrayList<String> metadataLines = lines.get(0);
        ArrayList<String> noteLines = lines.get(1);

        return new Song(
                getMetadata(metadataLines),
                getNotes(noteLines)
        );
    }

    public static SongMetadata getMetadata(String filePath) {
        String fileContent;
        try {
            fileContent = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }

        ArrayList<ArrayList<String>> lines = splitLines(fileContent);
        return getMetadata(lines.get(0));
    }

    private static NoteData parseNoteLine(String line) {
        String[] parts = line.split(",");
        int lane = Integer.parseInt(parts[0]);
        NoteType type = switch (Integer.parseInt(parts[1])) {
            case 0 -> NoteType.Note;
//            case 1 -> NoteType.HoldNote;
            default -> throw new IllegalArgumentException("Invalid note type: " + parts[1]);
        };
        int startTime = Integer.parseInt(parts[2]);
        int duration = Integer.parseInt(parts[3]);

        return new NoteData(lane, type, startTime, duration);
    }

    public static LinkedList<NoteData> getNotes(List<String> noteLines) {
        LinkedList<NoteData> notes = new LinkedList<>();
        for (String line : noteLines) {
            NoteData data = parseNoteLine(line);
            notes.add(data);
        }
        return notes;
    }

    public static SongMetadata getMetadata(List<String> metadata) {
        String name = getValue(metadata.get(0));
        String artist = getValue(metadata.get(1));
        int bpm = Integer.parseInt(getValue(metadata.get(2)));
        int duration = Integer.parseInt(getValue(metadata.get(3)));
        Difficulty difficulty = Difficulty.values()[Integer.parseInt(getValue(metadata.get(4)))];

        return new SongMetadata(name, artist, bpm, duration, difficulty);
    }

    private static String getValue(String line) {
        String[] parts = line.split(":", 2);
        return parts[1].trim();
    }
}
