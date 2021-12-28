
package vilcode;

import java.awt.image.BufferedImage;

public class Filter {
    private final int size;
    private final Type type;
    private final int[][] mask;
    public enum Type {
            GAUSS,
            NORMAL
    }

    public Filter(int size, Type type, int... args){
        if(args.length != size * size)
            throw new RuntimeException("Amount of values should be equal a square of size!");
        this.size = size;
        this.type = type;
        mask = new int[size][size];
        for(int row = 0, i = 0; row < size; row++){
            for(int col = 0; col < size; col++, i++){
                mask[row][col] = args[i];
            }
        }
    }

    public int getMaskedPixelColor(BufferedImage image, int x, int y){
        //przechodzimy przez każdy piksel obrazka
        int pixelColor = 0;
        //uzycie maskowania bitowego jest spowodowane sposobem zapisu koloru piksela w BufferedImage
        // na każdym z 4 bajtów zapisany jest jeden kanał a,r,g,b
        int alpha = image.getRGB(x, y) & 0xff000000;
        int half = size / 2;
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                //stosujemy maskę na jednym kanale, gdyż każdy z kanałów rgb ma taką samą wartość.
                int currentPixelColor;
                int currentPixelXPosition = x + col - half;
                int currentPixelYPosition = y + row - half;
                if(
                        currentPixelXPosition >= 0 &&
                        currentPixelXPosition < image.getWidth() &&
                        currentPixelYPosition >= 0 &&
                        currentPixelYPosition < image.getHeight()
                )
                    currentPixelColor = image.getRGB(currentPixelXPosition, currentPixelYPosition);
                else{
                    currentPixelColor = image.getRGB(x, y);
                }
                currentPixelColor &= 0xff;
                int difference = currentPixelColor * mask[row][col];
                if(type == Type.GAUSS)
                    difference /= 159;
                pixelColor += difference;
            }
        }

        if(pixelColor < 0)
            pixelColor = 0;
        if (pixelColor > 255)
            pixelColor = 255;

        //przepisujemy wartość na każdy kanał
        int rgb = pixelColor;
        rgb <<= 8;
        rgb += pixelColor;
        rgb <<= 8;
        rgb += pixelColor;
        return rgb | alpha;
    }
}
