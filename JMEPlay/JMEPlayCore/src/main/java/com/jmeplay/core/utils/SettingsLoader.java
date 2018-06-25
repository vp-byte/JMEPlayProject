/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import com.jmeplay.core.utils.os.OSInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Store and load settings
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class SettingsLoader {

    private static final Logger logger = LoggerFactory.getLogger(SettingsLoader.class.getName());

    private String filename;
    private Properties settings;

    public SettingsLoader() {
        settings = new Properties();
    }

    /**
     * Load settings from file
     *
     * @param filename to load settings
     */
    public void loadSettings(String filename) {
        this.filename = filename;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(filename);
            settings.loadFromXML(inputStream);
            inputStream.close();
            logger.trace("{} success to load {} setting", SettingsLoader.class, filename);
        } catch (IOException e) {
            logger.info("{} fail to load {} setting", SettingsLoader.class, filename);
        }
    }

    /**
     * Write settings to in {@link #loadSettings(String)} defined file
     */
    public void writeSettings() {
        File path = new File(filename).getParentFile();
        if (!path.exists()) {
            if (path.mkdir()) {
                logger.error("{} create dir {} for setting", SettingsLoader.class, path);
                throw new IllegalArgumentException("Can't create directory " + path);
            }
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filename);
            settings.storeToXML(outputStream, "Settings for " + filename + " application");
        } catch (IOException e) {
            logger.error("{} fail to write {} setting", SettingsLoader.class, filename);
            throw new IllegalArgumentException("Can't write settings file", e);
        }
    }

    /**
     * Delete settings file from system
     */
    public void deleteSettings() {
        File file = new File(filename);
        if (!file.delete()) {
            logger.error("{} fail to delete {} setting", SettingsLoader.class, filename);
            throw new IllegalArgumentException("Can't delete " + filename);
        }
    }

    /**
     * Get value as string by key
     *
     * @param key for value
     * @return value
     */
    private String value(String key) {
        String option = settings.getProperty(key);
        logger.trace("{} -> key {}, value: {}", SettingsLoader.class, key, option);
        return option == null || option.equals("null") ? null : option;
    }

    /**
     * Get value as string by key
     *
     * @param key          for value
     * @param defaultValue if key-value not exists
     * @return string value
     */
    public String value(String key, String defaultValue) {
        String option = value(key);
        if (option == null) {
            logger.trace("{} -> key {}, defaultValue: {}", SettingsLoader.class, key, defaultValue);
            setValue(key, defaultValue);
            return defaultValue;
        }
        return option;
    }

    /**
     * Get value as boolean by key
     *
     * @param key          for value
     * @param defaultValue if key-value not exists
     * @return boolean value
     */
    public Boolean value(String key, Boolean defaultValue) {
        String option = value(key);
        if (option == null) {
            logger.trace("{} -> key {}, Boolean defaultValue: {}", SettingsLoader.class, key, defaultValue);
            setValue(key, "" + defaultValue);
            return defaultValue;
        }
        return Boolean.parseBoolean(option);
    }

    /**
     * Get value as integer by key
     *
     * @param key          for value
     * @param defaultValue if key-value not exists
     * @return integer value
     */
    public Integer value(String key, Integer defaultValue) {
        String option = value(key);
        if (option == null) {
            logger.trace("{} -> key {}, Integer defaultValue: {}", SettingsLoader.class, key, defaultValue);
            setValue(key, "" + defaultValue);
            return defaultValue;
        }
        return Integer.parseInt(option);
    }

    /**
     * Get value as long by key
     *
     * @param key          for value
     * @param defaultValue if key-value not exists
     * @return long value
     */
    public Long value(String key, Long defaultValue) {
        String option = value(key);
        if (option == null) {
            logger.trace("{} -> key {}, Long defaultValue: {}", SettingsLoader.class, key, defaultValue);
            setValue(key, "" + defaultValue);
            return defaultValue;
        }
        return Long.parseLong(option);
    }

    /**
     * Get value as float by key
     *
     * @param key          for value
     * @param defaultValue if key-value not exists
     * @return float value
     */
    public Float value(String key, Float defaultValue) {
        String option = value(key);
        if (option == null) {
            logger.trace("{} -> key {}, Float defaultValue: {}", SettingsLoader.class, key, defaultValue);
            setValue(key, "" + defaultValue);
            return defaultValue;
        }
        return Float.parseFloat(option);
    }

    /**
     * Get value as double by key
     *
     * @param key          for value
     * @param defaultValue if key-value not exists
     * @return double value
     */
    public Double value(String key, Double defaultValue) {
        String option = value(key);
        if (option == null) {
            logger.trace("{} -> key {}, Double defaultValue: {}", SettingsLoader.class, key, defaultValue);
            setValue(key, "" + defaultValue);
            return defaultValue;
        }
        return Double.parseDouble(option);
    }

    /**
     * Set boolean value
     *
     * @param key   for value
     * @param value to set
     */
    public void setValue(String key, Boolean value) {
        settings.setProperty(key, "" + value);
    }

    /**
     * Set string value
     *
     * @param key   for value
     * @param value to set
     */
    public void setValue(String key, String value) {
        settings.setProperty(key, value);
    }

    /**
     * Set integer value
     *
     * @param key   for value
     * @param value to set
     */
    public void setValue(String key, Integer value) {
        settings.setProperty(key, "" + value);
    }

    /**
     * Set long value
     *
     * @param key   for value
     * @param value to set
     */
    public void setValue(String key, Long value) {
        settings.setProperty(key, "" + value);
    }

    /**
     * Set float value
     *
     * @param key   for value
     * @param value to set
     */
    public void setValue(String key, Float value) {
        settings.setProperty(key, "" + value);
    }

    /**
     * Set double value
     *
     * @param key   for value
     * @param value to set
     */
    public void setValue(String key, Double value) {
        settings.setProperty(key, "" + value);
    }

    /**
     * Remove value by key
     *
     * @param key for value
     */
    public void removeValue(String key) {
        settings.remove(key);
    }

}
