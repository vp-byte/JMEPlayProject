/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ResourceBundleLoader;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * Localization for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleLocalization {

    // LOCALIZATION
    private final static String LOCALIZATION_CONSOLE_RESOURCEBUNDLE = "JMEPlayConsole";
    final static String LOCALIZATION_CONSOLE_CONSOLE = "console";
    final static String LOCALIZATION_CONSOLE_DESCRIPTION = "description";
    final static String LOCALIZATION_CONSOLE_CLOSE_TOOLTIP = "closetooltip";
    final static String LOCALIZATION_CONSOLE_COPY = "copy";
    final static String LOCALIZATION_CONSOLE_COPY_TOOLTIP = "copytooltip";
    final static String LOCALIZATION_CONSOLE_SELECTALL = "selectall";
    final static String LOCALIZATION_CONSOLE_SELECTALL_TOOLTIP = "selectalltooltip";
    final static String LOCALIZATION_CONSOLE_CLEARALL = "clearall";
    final static String LOCALIZATION_CONSOLE_CLEARALL_TOOLTIP = "clearalltooltip";
    final static String LOCALIZATION_CONSOLE_EXCEPTION_TOOLTIP = "exceptiontooltip";

    private ResourceBundle bundle;

    /**
     * Gets a string for the given key from JMEPlayConsole resource bundle
     *
     * @param key for value
     * @return localized string
     */
    final String getString(String key) {
        if (bundle == null) {
            bundle = ResourceBundleLoader.load(this.getClass().getClassLoader(), LOCALIZATION_CONSOLE_RESOURCEBUNDLE);
        }
        return bundle.getString(key);
    }

}
