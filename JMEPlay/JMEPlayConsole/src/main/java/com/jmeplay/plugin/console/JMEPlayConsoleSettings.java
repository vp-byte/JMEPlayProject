/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.console;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.utils.SettingsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * All console settings
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleSettings extends SettingsLoader {

    private final JMEPlayGlobalSettings jmePlayGlobalSettings;

    /**
     * Specify settings of console
     *
     * @param jmePlayGlobalSettings to configure default settings
     */
    @Autowired
    public JMEPlayConsoleSettings(JMEPlayGlobalSettings jmePlayGlobalSettings) {
        super();
        this.jmePlayGlobalSettings = jmePlayGlobalSettings;
    }

    /**
     * Load settings
     */
    @PostConstruct
    private void init() {
        loadSettings(JMEPlayConsoleResources.SETTINGSFILE);
    }

    /**
     * Write settings
     */
    @PreDestroy
    private void destroy() {
        writeSettings();
    }

    /**
     * Write or not exceptions to console
     *
     * @return option
     */
    public boolean writeExceptions() {
        return value(JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS, JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS_DEFAULT);
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

    /**
     * Default icon spacing
     * {@link JMEPlayGlobalSettings#iconSpacing()}
     *
     * @return icon size
     */
    public int iconSpacing() {
        return jmePlayGlobalSettings.iconSpacing();
    }

}
