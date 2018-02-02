/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * Localization for JMEPLayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsLocalization {

    // LOCALIZATION
    private final static String LOCALISATION_ASSETS_RESOURCEBUNDLE = "JMEPlayAssets";
    final static String LOCALISATION_ASSETS_ASSETS = "assets";
    final static String LOCALISATION_ASSETS_DESCRIPTION = "description";


    public final static String LOCALISATION_ASSETS_HANDLER_NEW = "new";
    public final static String LOCALISATION_ASSETS_HANDLER_OPEN = "open";
    public final static String LOCALISATION_ASSETS_HANDLER_OPENEXTERNAL = "openexternal";
    public final static String LOCALISATION_ASSETS_HANDLER_CUT = "cut";
    public final static String LOCALISATION_ASSETS_HANDLER_COPY = "copy";
    public final static String LOCALISATION_ASSETS_HANDLER_PASTE = "paste";
    public final static String LOCALISATION_ASSETS_HANDLER_RENAME = "rename";

    public final static String LOCALISATION_ASSETS_HANDLER_DELETE = "delete";
    public final static String LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_TITLE = "deleteconfirmtitle";
    public final static String LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_QUESTION = "deleteconfirmquestion";

    public final static String LOCALISATION_ASSETS_HANDLER_OPENSYSTEMEXPLORER = "opensystemexplorer";

    public final static String LOCALISATION_ASSETS_HANDLER_NEW_FOLDER = "newfolder";
    public final static String LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TITLE = "newfoldertitle";
    public final static String LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TEXT = "newfoldertext";


    private ResourceBundle bundle;

    public final String value(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_RESOURCEBUNDLE);
        }
        return bundle.getString(key);
    }

}
