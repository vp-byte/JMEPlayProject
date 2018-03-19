/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets;

import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Root node for JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsRoot extends BorderPane {

    private final JMEPlayAssetsTreeView jmePlayAssetsTreeView;

    /**
     * Constructor to create root node
     *
     * @param jmePlayAssetsTreeView tree view of assets component
     */
    @Autowired
    public JMEPlayAssetsRoot(JMEPlayAssetsTreeView jmePlayAssetsTreeView) {
        this.jmePlayAssetsTreeView = jmePlayAssetsTreeView;
    }

    /**
     * Initialize root node of assets component
     */
    @PostConstruct
    private void init() {
        setMinHeight(0);
        getStylesheets().add(getClass().getResource(JMEPlayAssetsResources.CSS).toExternalForm());
        setCenter(jmePlayAssetsTreeView);
    }

}
