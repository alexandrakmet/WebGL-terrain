import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Scanner;

public class Heightmap {
    private int[][] pixels;
    private String name = "image.png";

    public Heightmap() {
        readMatrix();
        createImage();
    }

    private void readMatrix() {
        Scanner sc = null;
        try {
            sc = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Alexandra\\Desktop\\Studies\\3\\2\\OOP\\lab11111\\src\\main\\resources\\heightmap.txt")));
        } catch (FileNotFoundException ignored) {
            ignored.printStackTrace();
        }

        String[] line = sc.nextLine().trim().split(" ");

        pixels = new int[Integer.parseInt(line[0])][Integer.parseInt(line[1])];
        while (sc.hasNextLine()) {
            for (int i = 0; i < pixels.length; i++) {
                line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    pixels[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
    }

    private void createImage() {
        BufferedImage image = new BufferedImage(pixels.length, pixels.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                int a = pixels[i][j];
              //  Color newColor = new Color(a, a, a);
                image.setRGB(j, i, a);
            }
        }
        File output = new File("C:\\Users\\Alexandra\\Desktop\\Studies\\3\\2\\OOP\\lab11111\\src\\main\\webapp\\image.png");
        try {
            ImageIO.write(image, "png", output);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public String encoder() {
        String base64Image = "";
        File file = new File("C:\\Users\\Alexandra\\Desktop\\Studies\\3\\2\\OOP\\lab11111\\src\\main\\webapp\\image.png");
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        System.out.println(base64Image);
        return base64Image;
    }

   /* public ByteBuffer createByteBuffer() throws IOException {
        File f = new File("C:\\Users\\Alexandra\\Desktop\\Studies\\3\\2\\OOP\\lab11111\\src\\main\\webapp\\image.png");
        BufferedImage bi = ImageIO.read(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", out);
        ByteBuffer byteBuffer = ByteBuffer.wrap(out.toByteArray());

        ByteBuffer encode = Base64.getEncoder().encode()
        mSession.getBasicRemote().sendBinary(encode);
        out.close();
        byteBuffer.clear();
        encode.clear();
    }*/

    public String getImageName(){
        return name;
    }
}
