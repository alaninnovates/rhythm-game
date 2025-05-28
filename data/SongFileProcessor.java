package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import components.Note;
import components.NoteType;

public class SongFileProcessor {

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

        LinkedList<NoteData> noteDataList = new LinkedList<>();
        int songLength = 0;

        for (String line : noteLines) {
            NoteData data = parseNoteLine(line);
            noteDataList.add(data);
            songLength = Math.max(songLength, data.startTime() + data.duration());
        }

        LinkedList<Note> notes = new LinkedList<>();
        int screenMid = 0;

        ListIterator<NoteData> iterator = noteDataList.listIterator(noteDataList.size());
        while (iterator.hasPrevious()) {
            NoteData data = iterator.previous();
            int y = screenMid - (songLength - data.startTime());
            notes.add(new Note(data.type(), data.lane(), y, data.duration()));
        }

        return processSongLines(metadataLines, notes);
    }

    public static String readFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }

    public static ArrayList<ArrayList<String>> splitLines(String fileContent) {
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

    public static NoteData parseNoteLine(String line) {
        String[] parts = line.split(",");
        int lane = Integer.parseInt(parts[0]);
        NoteType type = switch (Integer.parseInt(parts[1])) {
            case 0 -> NoteType.Note;
            case 1 -> NoteType.HoldNote;
            default -> throw new IllegalArgumentException("Invalid note type: " + parts[1]);
        };
        int startTime = Integer.parseInt(parts[2]);
        int duration = Integer.parseInt(parts[3]);

        return new NoteData(lane, type, startTime, duration);
    }

    public static Song processSongLines(List<String> metadata, LinkedList<Note> notes) {
        String name = getValue(metadata.get(0));
        String artist = getValue(metadata.get(1));
        int bpm = Integer.parseInt(getValue(metadata.get(2)));
        Difficulty difficulty = Difficulty.values()[Integer.parseInt(getValue(metadata.get(3)))];

        return new Song(name, artist, bpm, difficulty, notes);
    }

    private static String getValue(String line) {
        String[] parts = line.split(":", 2);
        return parts[1].trim();
    }
}
