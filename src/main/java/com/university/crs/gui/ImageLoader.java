package com.university.crs.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

/**
 * Utility class for loading images from resources.
 */
public class ImageLoader {
    
    /**
     * Load an image from resources/images folder
     * @param filename The image filename (e.g., "graduation-cap.png")
     * @param width Desired width (0 to preserve original)
     * @param height Desired height (0 to preserve original)
     * @return ImageView with the loaded image, or null if not found
     */
    public static ImageView loadImage(String filename, double width, double height) {
        try {
            InputStream stream = ImageLoader.class.getResourceAsStream("/images/" + filename);
            if (stream != null) {
                Image image = new Image(stream);
                ImageView imageView = new ImageView(image);
                
                if (width > 0) {
                    imageView.setFitWidth(width);
                }
                if (height > 0) {
                    imageView.setFitHeight(height);
                }
                
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                
                return imageView;
            }
        } catch (Exception e) {
            System.err.println("Could not load image: " + filename);
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Check if an image exists in resources
     * @param filename The image filename
     * @return true if image exists
     */
    public static boolean imageExists(String filename) {
        try {
            InputStream stream = ImageLoader.class.getResourceAsStream("/images/" + filename);
            if (stream != null) {
                stream.close();
                return true;
            }
        } catch (Exception e) {
            // Image doesn't exist
        }
        return false;
    }
}
