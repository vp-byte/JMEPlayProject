/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.TestFxApplicationTest;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test to load SvgImageLoaderFactory
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see JMEPlay
 */
public class SvgImageLoaderFactoryTest extends TestFxApplicationTest {

    @Override
    public void start(Stage stage) {
        super.start(stage);
        SvgImageLoaderFactory.install();
    }

    @Test
    public void testShow() {
        Image image = new Image(SvgImageLoaderFactoryTest.class.getResourceAsStream("/icons/testicon.svg"));
        Assert.assertNotNull(image);
        Assert.assertTrue(image.getWidth() > 0.0);
        Assert.assertTrue(image.getHeight() > 0.0);
    }

}