/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import javafx.scene.image.ImageView;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Test for {@link ImageLoader}
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class ImageLoaderTest {

    /**
     * Test load image view with default size
     */
    @Test
    public void imageView() {
        ImageView imageView = ImageLoader.imageView(this.getClass(), "/icons/testicon.svg");
        Assert.assertNotNull(imageView);
    }

    /**
     * Test load image view with defined size
     */
    @Test
    public void imageViewWidthHeight() {
        ImageView imageView = ImageLoader.imageView(this.getClass(), "/icons/testicon.svg", 32, 32);
        Assert.assertNotNull(imageView);
    }

}