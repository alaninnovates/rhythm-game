package lib;

import javax.swing.*;
import java.net.URL;

public class AssetLoader {
    public static ImageIcon loadImage(String path) {
        URL resourceURL = AssetLoader.class.getResource("/" + path);
        if (resourceURL == null) {
            throw new RuntimeException("Resource not found: " + path);
        }
        return new ImageIcon(resourceURL);
    }
}