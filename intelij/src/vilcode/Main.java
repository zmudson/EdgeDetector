package vilcode;

import vilcode.utils.ImageLoader;
import vilcode.utils.ImageProcessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage image = null;
        String path;
        String name;
        String imageType;

        do {
            System.out.print("Enter the filename: ");
            path = scanner.next();
           // parsing name
            int ind = path.lastIndexOf(".");
            name = path.substring(0,ind);
            imageType = path.substring(ind+1);
            try{
                 image = imageLoader.loadImage(path);
            }catch (IOException ioException){
                System.out.println("Something went wrong! Please try again.");
            }
        }while (image == null);
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.grayScale(image);
        Filter filter = new Filter(3, Filter.Type.NORMAL,-2, 1, -2, 1, 4, 1, -2, 1, -2);
        Filter gauss = new Filter(5, Filter.Type.GAUSS,
                2,3,5,4,2,
                4,9,12,9,4,
                5,12,15,12,5,
                4,9,12,9,4,
                2,4,5,4,2);
        BufferedImage gaussImage = imageProcessor.mask(image, gauss);
        BufferedImage detectedEdges = imageProcessor.mask(gaussImage, filter);
        BufferedImage thresholdImage = imageProcessor.threshold(detectedEdges, 10, 25);
        imageLoader.saveImage(thresholdImage, name, imageType);
    }


}
