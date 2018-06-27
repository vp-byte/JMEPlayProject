/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Loader for resource bundle with language and utf-8 support
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class ResourceBundleLoader {

    /**
     * Load resource bundle
     *
     * @param classLoader specific class loader
     * @param resource    name of resource
     * @return loaded resource depends on default locale
     */
    public static ResourceBundle load(final ClassLoader classLoader, final String resource) {
        final String language = Locale.getDefault().getLanguage();
        String path = resource + ".properties";
        if (classLoader.getResource(resource + "_" + language + ".properties") != null) {
            path = resource + "_" + language + ".properties";
        }
        System.out.println(path);
        try {
            InputStream stream = classLoader.getResourceAsStream(path);
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            return new PropertyResourceBundle(reader);
        } catch (Exception e) {
            throw new IllegalStateException("Cant't load ResourceBundle from " + path, e);
        }
    }
}
