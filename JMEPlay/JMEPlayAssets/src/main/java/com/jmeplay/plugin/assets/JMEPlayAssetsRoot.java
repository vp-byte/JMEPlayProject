/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
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

    public final JMEPlayAssetsTreeView jmePlayAssetsTreeView;

    @Autowired
    public JMEPlayAssetsRoot(JMEPlayAssetsTreeView jmePlayAssetsTreeView) {
        this.jmePlayAssetsTreeView = jmePlayAssetsTreeView;
    }

    @PostConstruct
    private void init() {
        setMinHeight(0);
        getStylesheets().add(getClass().getResource(JMEPlayAssetsResources.CSS).toExternalForm());
        setCenter(jmePlayAssetsTreeView);
    }
}
