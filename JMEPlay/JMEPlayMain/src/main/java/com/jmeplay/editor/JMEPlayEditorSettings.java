/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.utils.SettingsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * All settings for JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorSettings extends SettingsLoader {

    private final JMEPlayGlobalSettings jmePlayGlobalSettings;

    /**
     * Constructor to create editor settings
     *
     * @param jmePlayGlobalSettings to configure editor settings
     */
    @Autowired
    public JMEPlayEditorSettings(JMEPlayGlobalSettings jmePlayGlobalSettings) {
        super();
        this.jmePlayGlobalSettings = jmePlayGlobalSettings;
    }

    /**
     * Load editor settings from file
     */
    @PostConstruct
    private void init() {
        loadSettings(JMEPlayEditorResources.SETTINGSFILE);
    }

    /**
     * Write editor settings to file
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
     * Default icon size for bar
     * {@link JMEPlayGlobalSettings#iconSizeBar()}
     *
     * @return bars icon size
     */
    public int iconSizeBar() {
        return jmePlayGlobalSettings.iconSizeBar();
    }

    /**
     * Default spacing between icons
     * {@link JMEPlayGlobalSettings#iconSpacing()}
     *
     * @return icon spacing
     */
    public int iconSpacing() {
        return jmePlayGlobalSettings.iconSpacing();
    }

    /**
     * Visibility of border bars
     *
     * @return visibility
     */
    public boolean borderBarsVisible() {
        return value(JMEPlayEditorResources.BORDER_BARS_VISIBLE, JMEPlayEditorResources.BORDER_BARS_VISIBLE_DEFAULT);
    }

}
