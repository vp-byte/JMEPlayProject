/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.core.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Loader for image from resources folder
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class ImageLoader {

	/**
	 * Loads image and create ImageView with original width and height
	 * 
	 * @param clazz to resolve resource from class loader
	 * @param imagePath path in resource folder
	 * @return {@link ImageView} of JavaFX
	 */
	public static ImageView imageView(Class<?> clazz, String imagePath) {
		return imageView(clazz, imagePath, null, null);
	}

	/**
	 * Loads image and create ImageView with defined width and height
	 * 
	 * @param clazz to resolve resource from class loader
	 * @param imagePath path in resource folder
	 * @param width to set {@link ImageView#setFitWidth(double)}
	 * @param height to set {@link ImageView#setFitHeight(double)}
	 * @return {@link ImageView} of JavaFX
	 */
	public static ImageView imageView(Class<?> clazz, String imagePath, Integer width, Integer height) {
		Image image = new Image(clazz.getResourceAsStream(imagePath));
		ImageView imageView = new ImageView(image);

		if (width != null)
			imageView.setFitWidth(width);
		if (height != null)
			imageView.setFitHeight(height);
		return imageView;
	}

}
