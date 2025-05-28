package lib;

import java.awt.Image;

public class AssetImage {
    private final String imagePath;
    private Image cachedImage;

    public AssetImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image getImage() {
        if (cachedImage == null) {
            cachedImage = AssetLoader.loadImage(imagePath).getImage();
        }
        return cachedImage;
    }
}
