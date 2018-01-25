/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
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

    public boolean writeExceptions() {
        return value(JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS, JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS_DEFAULT);
    }

    public int iconSize() {
        return jmePlayGlobalSettings.iconSize();
    }

    public int iconSizeBar() {
        return jmePlayGlobalSettings.iconSizeBar();
    }

    public int iconSpacing() {
        return jmePlayGlobalSettings.iconSpacing();
    }

}
