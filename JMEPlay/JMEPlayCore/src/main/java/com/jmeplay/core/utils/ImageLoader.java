/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import com.jmeplay.core.utils.os.OSInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Loader for image from resources folder
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class ImageLoader {

    private static final Logger logger = LoggerFactory.getLogger(OSInfo.class.getName());

    /**
     * Loads image and create ImageView with original width and height
     *
     * @param clazz     to extension resource from class loader
     * @param imagePath path in resource folder
     * @return {@link ImageView} of JavaFX
     */
    public static ImageView imageView(Class<?> clazz, String imagePath) {
        return imageView(clazz, imagePath, null, null);
    }

    /**
     * Loads image and create ImageView with defined width and height
     *
     * @param clazz     to extension resource from class loader
     * @param imagePath path in resource folder
     * @param width     to set {@link ImageView#setFitWidth(double)}
     * @param height    to set {@link ImageView#setFitHeight(double)}
     * @return {@link ImageView} of JavaFX
     */
    public static ImageView imageView(Class<?> clazz, String imagePath, Integer width, Integer height) {
        logger.trace("ImageView from {}, image path: {}, width: {}, height: {}", ImageLoader.class, imagePath, width, height);

        Image image = new Image(clazz.getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        if (width != null)
            imageView.setFitWidth(width);
        if (height != null)
            imageView.setFitHeight(height);
        return imageView;
    }

}
