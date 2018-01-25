/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.console;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

/**
 * Localization for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleLocalization {

    // LOCALIZATION
    private final static String CONSOLE_RESOURCEBUNDLE_LOCALIZATION = "JMEPlayConsole";
    final static String CONSOLE_LOCALIZATION_CONSOLE = "console";
    final static String CONSOLE_LOCALIZATION_DESCRIPTION = "description";
    final static String CONSOLE_LOCALIZATION_CLOSE_TOOLTIP = "closetooltip";
    final static String CONSOLE_LOCALIZATION_COPY = "copy";
    final static String CONSOLE_LOCALIZATION_COPY_TOOLTIP = "copytooltip";
    final static String CONSOLE_LOCALIZATION_SELECTALL = "selectall";
    final static String CONSOLE_LOCALIZATION_SELECTALL_TOOLTIP = "selectalltooltip";
    final static String CONSOLE_LOCALIZATION_CLEARALL = "clearall";
    final static String CONSOLE_LOCALIZATION_CLEARALL_TOOLTIP = "clearalltooltip";
    final static String CONSOLE_LOCALIZATION_EXCEPTION_TOOLTIP = "exceptiontooltip";

    private ResourceBundle bundle;

    /**
     * Gets a string for the given key from JMEPlayConsole resource bundle
     *
     * @param key for value
     * @return localized string
     */
    public final String getString(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlayConsoleLocalization.CONSOLE_RESOURCEBUNDLE_LOCALIZATION);
        }
        return bundle.getString(key);
    }

}
