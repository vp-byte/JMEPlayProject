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
	public ReadOnlyObjectWrapper<String> getAssetfolderChange();

	public String getAssetfolder();

	// ToolBar
	public ToolBar getToolBar();

	// TabPane
	public TabPane getTabPane();

	// Root
	public Parent getRoot();

	// Stage
	public Stage getStage();

	// Scene
	public Scene getScene();

	public ReadOnlyObjectProperty<Scene> getSceneChange();

}
