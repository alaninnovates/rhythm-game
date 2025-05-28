package lib;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FontLoader {
    public static void loadFonts() {
        final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final List<String> existingFonts = Arrays.asList(graphicsEnvironment.getAvailableFontFamilyNames());
        final List<File> files = Arrays.asList(
                new File("assets/Yellowtail-Regular.ttf"),
                new File("assets/OpenSans-VariableFont_wdth,wght.ttf")
        );
        for (File file : files) {
            if (file.exists()) {
                Font font = null;
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, file);
                } catch (FontFormatException | IOException e) {
                    e.printStackTrace();
                }
                if (!existingFonts.contains(font.getFontName())) {
                    graphicsEnvironment.registerFont(font);
                }
            }
        }
    }
}
