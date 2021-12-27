package vilcode;

import vilcode.utils.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage image = null;

        do {
            System.out.print("Enter the filename: ");
            String path = "/resources/images/" + scanner.next();
            try{
                 image = imageLoader.loadImage(path);
            }catch (IOException ioException){
                System.out.println("Something went wrong! Please try again.");
            }
        }while (image == null);
        image.getRGB(1,1);
        System.out.println("end");
    }


}
