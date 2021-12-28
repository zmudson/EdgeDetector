package vilcode.utils;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    private final int[][] filter;
    private final int maskSize = 3;

    //maska za pomocą, której nastąpi wykrywanie krawędzi
    public ImageProcessor(){
        filter = new int[maskSize][maskSize];

        filter[0][0] = 0;
        filter[0][1] = 1;
        filter[0][2] = 2;
        filter[1][0] = -1;
        filter[1][1] = 0;
        filter[1][2] = 1;
        filter[2][0] = -2;
        filter[2][1] = -1;
        filter[2][2] = 0;

    }

    //funkcja zamieniająca obraz na czarno-biały
    public void grayScale(BufferedImage image){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                image.setRGB(x, y, grayOutPixel(image.getRGB(x, y)));
            }
        }
    }
    //funkcja, która generuje obraz z wykrytymi krawędziami na podstawie obrazu czarno-białego
    public BufferedImage detectEdges(BufferedImage image){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = maskSize/2; y < image.getHeight() - maskSize/2; y++)
        {
            for (int x = maskSize/2; x < image.getWidth() - maskSize/2; x++)
            {
                //przechodzimy przez każdy piksel obrazka
                int pixelColor = 0;
                //uzycie maskowania bitowego jest spowodowane sposobem zapisu koloru piksela w BufferedImage
                // na każdym z 4 bajtów zapisany jest jeden kanał a,r,g,b
                int alpha = image.getRGB(x, y) & 0xff000000;
                for (int k = 0; k < maskSize; k++)
                {
                    for (int z = 0; z < maskSize; z++)
                    {
                        //stosujemy maskę na jednym kanale, gdyż każdy z kanałów rgb ma taką samą wartość.
                        pixelColor += (image.getRGB(x - maskSize/2 + k, y - maskSize/2 + z) & 0xff) * filter[k][z];
                    }
                }
                //przepisujemy wartość na każdy kanał
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
