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
    final static String ASSETS_RESOURCEBUNDLE_ASSETS = "JMEPlayAssets";
    final static String ASSETS_LOCALISATION_ASSETS = "assets";
    final static String ASSETS_LOCALIZATION_DESCRIPTION = "description";

    private ResourceBundle bundle;

    /**
     * Gets a string for the given key from JMEPlayImageLocalization resource bundle
     *
     * @param key
     * @return
     */
    public final String getString(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlayAssetsLocalization.ASSETS_RESOURCEBUNDLE_ASSETS);
        }
        return bundle.getString(key);
    }

}
