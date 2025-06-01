package data;

import java.util.LinkedList;

public record Song(
        SongMetadata metadata,
        LinkedList<NoteData> notes
) {
}
