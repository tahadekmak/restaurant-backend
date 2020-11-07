package cme.restaurantbackend.PostgreSQL;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageToByteArray {
    public static void main(String args[]) throws Exception{
        InputStream in = ImageToByteArray.class.getClassLoader().getResourceAsStream("images/kfc_image.jpg");
        System.out.println(in);
        BufferedImage bImage = ImageIO.read(in);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        System.out.println(data);

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("output.jpg") );

        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(bImage2));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}