package data;

import components.Note;

import java.util.LinkedList;

public record Song(
        String name,
        String artist,
        int bpm,
        Difficulty difficulty,
        LinkedList<Note> notes
) {
}
