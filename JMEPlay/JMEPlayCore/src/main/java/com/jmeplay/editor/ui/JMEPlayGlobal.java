/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
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
    ReadOnlyObjectProperty<String> assetFolderChange();

    // ToolBar
    ToolBar toolBar();

    // TabPane
    TabPane tabPane();

    // Root
    Parent root();

    // Stage
    Stage stage();

    // Scene
    Scene scene();

    ReadOnlyObjectProperty<Scene> sceneChange();

}
