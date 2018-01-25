/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.console;

import com.jmeplay.core.JMEPlayGlobalResources;

/**
 * Resources for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class JMEPlayConsoleResources {

    public final static String SETTINGSFILE = JMEPlayGlobalResources.SETTINGSFOLDER + "ConsoleSettings.xml";

    // CSS
    public final static String CSS = JMEPlayGlobalResources.CSS + "plugin/console/JMEPlayConsole.css";

    // ICONS
    final static String ICONS_CONSOLE_CONSOLE = JMEPlayGlobalResources.ICONS + "plugin/console/JMEPlayConsole.svg";
    final static String ICONS_CONSOLE_CLOSE = JMEPlayGlobalResources.ICONS + "plugin/console/JMEPlayConsoleClose.svg";
    final static String ICONS_CONSOLE_EXCEPTION = JMEPlayGlobalResources.ICONS + "plugin/console/JMEPlayConsoleException.svg";
    final static String ICONS_CONSOLE_COPY = JMEPlayGlobalResources.ICONS + "plugin/console/JMEPlayConsoleCopy.svg";
    final static String ICONS_CONSOLE_DELETE = JMEPlayGlobalResources.ICONS + "plugin/console/JMEPlayConsoleDelete.svg";
    final static String ICONS_CONSOLE_SELECTALL = JMEPlayGlobalResources.ICONS + "plugin/console/JMEPlayConsoleSelectAll.svg";

    // KEYS FOR SETTINGS
    final static String CONSOLE_WRITE_EXCEPTIONS = "consoleWriteExceptions";
    final static boolean CONSOLE_WRITE_EXCEPTIONS_DEFAULT = false;

}
