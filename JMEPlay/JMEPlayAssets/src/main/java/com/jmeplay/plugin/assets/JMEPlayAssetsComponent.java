/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayComponent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Implementation for JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsComponent extends JMEPlayComponent {

    private Label label;

    private final JMEPlayAssetsSettings jmePlayAssetsSettings;
    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayAssetsRoot jmePlayAssetsRoot;

    /**
     * Constructor to create assets component
     *
     * @param jmePlayAssetsSettings     to configure assets
     * @param jmePlayAssetsLocalization to localize ui elements
     * @param jmePlayAssetsRoot         root node of assets
     */
    @Autowired
    public JMEPlayAssetsComponent(JMEPlayAssetsSettings jmePlayAssetsSettings,
                                  JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                                  JMEPlayAssetsRoot jmePlayAssetsRoot) {
        this.jmePlayAssetsSettings = jmePlayAssetsSettings;
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayAssetsRoot = jmePlayAssetsRoot;
    }

    /**
     * Initialize assets component
     */
    @PostConstruct
    private void init() {
        this.setPriority(0);
        setAlignment(Align.LEFT);
        createLabel();
    }

    /**
     * Create label for JMEPlayConsole
     */
    private void createLabel() {
        String labelText = jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_ASSETS);
        int iconSizeBar = jmePlayAssetsSettings.iconSizeBar();
        label = new Label(labelText, ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_ASSETS, iconSizeBar, iconSizeBar));
    }

    /**
     * {@link JMEPlayComponent:name}
     *
     * @return name
     */
    @Override
    public String name() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_ASSETS);
    }

    /**
     * {@link JMEPlayComponent:description}
     *
     * @return description
     */
    @Override
    public String description() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_DESCRIPTION);
    }

    /**
     * {@link JMEPlayComponent:label}
     *
     * @return label
     */
    @Override
    public Label label() {
        return label;
    }

    /**
     * {@link JMEPlayComponent:component}
     *
     * @return component as node
     */
    @Override
    public Node component() {
        return jmePlayAssetsRoot;
    }

}