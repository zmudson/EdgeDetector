package vilcode.utils;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    public void grayScale(BufferedImage image){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                image.setRGB(x, y, grayOutPixel(image.getRGB(x, y)));
            }
        }
    }

    private int grayOutPixel(int rgb){
        int blue = rgb & 0xff;
        rgb >>=  8;
        int green = rgb & 0xff;
        rgb >>=  8;
        int red = rgb & 0xff;
        rgb >>=  8;

        int gray = (int)(red * 0.2126 + green * 0.7152 + blue * 0.0722);

        for(int i = 0; i < 3; i++){
            rgb <<= 8;
            rgb |= gray;
        }
        return rgb;
    }
}
