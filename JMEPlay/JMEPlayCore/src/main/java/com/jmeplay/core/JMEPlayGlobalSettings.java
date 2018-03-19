/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.SettingsLoader;

/**
 * All global settings
 *
 * @author Vladimir Petrenko (vp-byte)
 */
@Component
public class JMEPlayGlobalSettings extends SettingsLoader {

    /**
     * Constructor to create global settings
     */
    public JMEPlayGlobalSettings() {
        super();
    }

    /**
     * Load global settings from file
     */
    @PostConstruct
    private void init() {
        loadSettings(JMEPlayGlobalResources.SETTINGSFILE);
    }

    /**
     * Write global settings to file
     */
    @PreDestroy
    private void destroy() {
        writeSettings();
    }

    /**
     * Selected root folder
     *
     * @return root folder
     */
    public String rootFolder() {
        return value(JMEPlayGlobalResources.ROOTFOLDER, System.getProperty("user.home"));
    }

    /**
     * Default icon size
     *
     * @return icon size
     */
    public int iconSize() {
        return value(JMEPlayGlobalResources.ICONSIZE, JMEPlayGlobalResources.ICONSIZE_DEFAULT);
    }

    /**
     * Default icon size for bar
     *
     * @return bars icon size
     */
    public int iconSizeBar() {
        return value(JMEPlayGlobalResources.ICONSIZE_BAR, JMEPlayGlobalResources.ICONSIZE_BAR_DEFAULT);
    }

    /**
     * Default spacing between icons
     *
     * @return icon spacing
     */
    public int iconSpacing() {
        return value(JMEPlayGlobalResources.SPACING, JMEPlayGlobalResources.SPACING_DEFAULT);
    }

}
