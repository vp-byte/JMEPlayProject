/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/**
 * Localization for JMEPLayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsLocalization {

    // LOCALIZATION
    final static String LOCALISATION_ASSETS_RESOURCEBUNDLE = "JMEPlayAssets";
    final static String LOCALISATION_ASSETS_ASSETS = "assets";
    final static String LOCALISATION_ASSETS_DESCRIPTION = "description";

    public final static String LOCALISATION_ASSETS_HANDLER_OPEN = "open";
    public final static String LOCALISATION_ASSETS_HANDLER_OPEN_TOOLTIP = "opentooltip";

    private ResourceBundle bundle;

    /**
     * Gets a string for the given key from JMEPlayImageLocalization resource bundle
     *
     * @param key
     * @return
     */
    public final String value(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_RESOURCEBUNDLE);
        }
        return bundle.getString(key);
    }

}
