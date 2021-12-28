package vilcode.utils;

import vilcode.Filter;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    //funkcja zamieniająca obraz na czarno-biały
    public void grayScale(BufferedImage image){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                image.setRGB(x, y, grayOutPixel(image.getRGB(x, y)));
            }
        }
    }
    //funkcja, która generuje obraz z wykrytymi krawędziami na podstawie obrazu czarno-białego
    public BufferedImage mask(BufferedImage image, Filter filter){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                newImage.setRGB(x, y, filter.getMaskedPixelColor(image, x, y));
            }
        }
        return newImage;
    }

    public BufferedImage threshold(BufferedImage image, int minThreshold, int maxThreshold){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelColor = image.getRGB(x, y);
                int alpha = pixelColor & 0xff000000;
                pixelColor &= 0xff;
                if(pixelColor >= maxThreshold)
                    pixelColor = 255;
                else if(pixelColor <= minThreshold)
                    pixelColor = 0;
                else{
                    pixelColor = 0;
                    if(x - 1 >= 0 && (image.getRGB(x - 1, y) & 0xff) >= maxThreshold)
                        pixelColor = 255;
                    else if(x + 1 < image.getWidth() && (image.getRGB(x + 1, y) & 0xff) >= maxThreshold)
                        pixelColor = 255;
                    else if(y - 1 >= 0 && (image.getRGB(x, y - 1) & 0xff) >= maxThreshold)
                        pixelColor = 255;
                    else if(y + 1 < image.getHeight() && (image.getRGB(x, y + 1) & 0xff) >= maxThreshold)
                        pixelColor = 255;
                }
                int rgb = pixelColor;
                rgb <<= 8;
                rgb += pixelColor;
                rgb <<= 8;
                rgb += pixelColor;
                newImage.setRGB(x, y, rgb | alpha);
            }
        }
        return newImage;
    }

    //zamiana piksela na kolor czarno-biały
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
