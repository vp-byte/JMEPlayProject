/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
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

    /**
     * Specify settings of assets
     *
     * @param jmePlayGlobalSettings to configure default settings
     */
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

    /**
     * Selected root folder
     * {@link JMEPlayGlobalSettings#rootFolder()}
     *
     * @return root folder
     */
    public String rootFolder() {
        return jmePlayGlobalSettings.rootFolder();
    }

    /**
     * Default icon size
     * {@link JMEPlayGlobalSettings#iconSize()}
     *
     * @return icon size
     */
    public int iconSize() {
        return jmePlayGlobalSettings.iconSize();
    }

    /**
     * Default icon size bar
     * {@link JMEPlayGlobalSettings#iconSizeBar()}
     *
     * @return icon size bar
     */
    public int iconSizeBar() {
        return jmePlayGlobalSettings.iconSizeBar();
    }

}
