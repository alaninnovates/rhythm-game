package components;

import data.NoteData;

public class Note {
    private final NoteData data;

    private int elapsedDuration = 0;
    private boolean held = false;

    public Note(NoteData data) {
        this.data = data;
    }

    public NoteData getData() {
        return data;
    }
}
