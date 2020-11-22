package cme.restaurantbackend;

import cme.restaurantbackend.controller.RestaurantController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MyFunctions {

    public static File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = MyFunctions.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }

    public static byte[] imageToByteArray(String path) throws IOException {
        InputStream in = RestaurantController.class.getClassLoader().getResourceAsStream(path);
        assert in != null;
        BufferedImage bImage = ImageIO.read(in);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        return bos.toByteArray();
    }
}
