package vilcode.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File("./"+path));
    }
    public void saveImage(BufferedImage image, String name, String type) throws IOException {
        ImageIO.write(image, type, new File("./"+name+"_edges."+type));
    }
}
