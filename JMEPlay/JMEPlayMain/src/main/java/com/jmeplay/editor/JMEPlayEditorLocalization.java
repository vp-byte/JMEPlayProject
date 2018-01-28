/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

/**
 * Localization for JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorLocalization {

    private final static String LOCALIZATION_EDITOR_RESOURCEBUNDLE = "JMEPlayEditor";

    public final static String LOCALIZATION_EDITOR_MENU_FILE = "file";
    public final static String LOCALIZATION_EDITOR_MENU_OPEN_ASSETS = "openassets";
    public final static String LOCALIZATION_EDITOR_MENU_OPEN_ASSETS_DIALOG_TITLE = "openassetsdialogtitle";
    public final static String LOCALIZATION_EDITOR_MENU_SETTINGS = "settings";
    public final static String LOCALIZATION_EDITOR_MENU_HELP = "help";

    private ResourceBundle bundle;

    public final String value(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_RESOURCEBUNDLE);
        }
        return bundle.getString(key);
    }

}
