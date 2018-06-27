/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Load resouce bundle depends on language
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class ResourceBundleLoaderTest {

    private static String bundleName = "JMEPLayCoreTest";

    /**
     * Load resource bundle with default locale
     */
    @Test
    public void load() {
        ResourceBundle bundle = ResourceBundleLoader.load(this.getClass().getClassLoader(), bundleName);
        Assert.assertNotNull(bundle);
    }

    /**
     * Load resource bundle with german locale
     */
    @Test
    public void loadGerman() {
        Locale.setDefault(new Locale("de", "DE"));
        ResourceBundle bundle = ResourceBundleLoader.load(this.getClass().getClassLoader(), bundleName);
        Assert.assertNotNull(bundle);
        Assert.assertEquals("DEUTSCH_äÄöÖüÜß_DEUTSCH", bundle.getString("name"));
    }

    /**
     * Try to load resource bundle with russian locale, no russian exist, load default
     */
    @Test
    public void loadGoToDefault() {
        Locale.setDefault(new Locale("ru", "RU"));
        ResourceBundle bundle = ResourceBundleLoader.load(this.getClass().getClassLoader(), bundleName);
        Assert.assertNotNull(bundle);
        Assert.assertEquals("ENGLISH", bundle.getString("name"));
    }
}