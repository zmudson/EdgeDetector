package vilcode.utils;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    private final int[][] filter;

    public ImageProcessor(){
        filter = new int[3][3];

        filter[0][0] = -2;
        filter[0][1] = 1;
        filter[0][2] = -2;
        filter[1][0] = 1;
        filter[1][1] = 4;
        filter[1][2] = 1;
        filter[2][0] = -2;
        filter[2][1] = 1;
        filter[2][2] = -2;

//        filter[0][0] = 0;
//        filter[0][1] = -1;
//        filter[0][2] = 0;
//        filter[1][0] = -1;
//        filter[1][1] = 4;
//        filter[1][2] = -1;
//        filter[2][0] = 0;
//        filter[2][1] = -1;
//        filter[2][2] = 0;
    }

    public void grayScale(BufferedImage image){
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                image.setRGB(x, y, grayOutPixel(image.getRGB(x, y)));
            }
        }
    }

    public BufferedImage detectEdges(BufferedImage image){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 1; y < image.getHeight() - 1; y++)
        {
            for (int x = 1; x < image.getWidth() - 1; x++)
            {
                int pixelColor = 0;
                int alpha = image.getRGB(x, y) & 0xff000000;
                for (int k = 0; k < 3; k++)
                {
                    for (int z = 0; z < 3; z++)
                    {
                        pixelColor += (image.getRGB(x - 1 + k, y - 1 + z) & 0xff) * filter[k][z];
                    }
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
