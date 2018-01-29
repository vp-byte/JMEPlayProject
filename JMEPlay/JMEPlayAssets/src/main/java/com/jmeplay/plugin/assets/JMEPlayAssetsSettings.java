/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.utils.SettingsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * All Assets settings
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsSettings extends SettingsLoader {

    private final JMEPlayGlobalSettings jmePlayGlobalSettings;

    @Autowired
    public JMEPlayAssetsSettings(JMEPlayGlobalSettings jmePlayGlobalSettings) {
        super();
        this.jmePlayGlobalSettings = jmePlayGlobalSettings;
    }

    /**
     * Load settings
     */
    @PostConstruct
    private void init() {
        loadSettings(JMEPlayAssetsResources.SETTINGSFILE);
    }

    /**
     * Write settings
     */
    @PreDestroy
    private void destroy() {
        writeSettings();
    }

    public String rootFolder() {
        return jmePlayGlobalSettings.rootFolder();
    }

    public int iconSize() {
        return jmePlayGlobalSettings.iconSize();
    }

    public int iconSizeBar() {
        return jmePlayGlobalSettings.iconSizeBar();
    }

}
