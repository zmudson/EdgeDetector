package vilcode.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public BufferedImage loadImage(String path) throws IOException {
        URL url = getClass().getResource(path);
        return ImageIO.read(url);
    }
    public void saveImage(BufferedImage image) throws IOException {
        ImageIO.write(image, "jpg", new File("./image.png"));
    }
}
