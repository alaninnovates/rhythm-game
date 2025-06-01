package data;

public record SongMetadata(
        String name,
        String artist,
        int bpm,
        int duration,
        Difficulty difficulty
) {
}
