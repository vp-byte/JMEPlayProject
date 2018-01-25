/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

/**
 * Interface to get all JMEPlay editor components and nodes to use it
 * application wide
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public interface JMEPlayGlobal {

    // Asset folder
    ReadOnlyObjectWrapper<String> getAssetfolderChange();

    // ToolBar
    ToolBar getToolBar();

    // TabPane
    TabPane getTabPane();

    // Root
    Parent getRoot();

    // Stage
    Stage getStage();

    // Scene
    Scene getScene();

    ReadOnlyObjectProperty<Scene> getSceneChange();

}
