package data;

import components.NoteType;

public record NoteData(int lane, NoteType type, int startTime, int duration) {
}
