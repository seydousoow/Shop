package com.sid.config;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@UtilityClass
public class Utils {

    // scale an image
    public static BufferedImage resizeImage(BufferedImage originalImage) {
        var resultingImage = originalImage.getScaledInstance(500, 500, 1);
        BufferedImage outputImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    // scale an image
    public static byte[] resizeImage(BufferedImage originalImage, String type, int width, int height) {
        var resultingImage = originalImage.getScaledInstance(width, height, 1);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return toByteArray(outputImage, type);
    }

    // BufferImage to Byte array
    public static byte[] toByteArray(BufferedImage bi, String format) {
        try (var baos = new ByteArrayOutputStream()){
            ImageIO.write(bi, format, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        var deflate = new Deflater();
        deflate.setInput(data);
        deflate.finish();
        try (var outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[1024];
            while (!deflate.finished()) outputStream.write(buffer, 0, deflate.deflate(buffer));
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    // decompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        var inflate = new Inflater();
        inflate.setInput(data);
        try (var outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[1024];
            while (!inflate.finished()) outputStream.write(buffer, 0, inflate.inflate(buffer));
            return outputStream.toByteArray();
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

}
