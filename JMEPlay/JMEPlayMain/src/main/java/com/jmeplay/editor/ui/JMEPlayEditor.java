/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import org.springframework.stereotype.Component;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;

/**
 * JMEPlayEditor component to handle all parts and nodes of the editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Data
@Component
public class JMEPlayEditor implements JMEPlayGlobal {

    // Asset folder
    private String assetfolder;
    private final ReadOnlyObjectWrapper<String> assetfolderChange = new ReadOnlyObjectWrapper<>();

    public void setAssetFolder(String assetfolder) {
        this.assetfolder = assetfolder;
        this.assetfolderChange.set(this.assetfolder);
    }

    // Stage
    private Stage stage;
    private final ReadOnlyObjectWrapper<Stage> stageChange = new ReadOnlyObjectWrapper<>();

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stageChange.set(this.stage);
    }

    // Root
    private Parent root;
    private final ReadOnlyObjectWrapper<Parent> rootChange = new ReadOnlyObjectWrapper<>();

    public void setRoot(Parent root) {
        this.root = root;
        this.rootChange.set(this.root);
    }

    // Scene
    private Scene scene;
    private final ReadOnlyObjectWrapper<Scene> sceneChange = new ReadOnlyObjectWrapper<>();

    public void setScene(Scene scene) {
        this.scene = scene;
        this.sceneChange.set(this.scene);
    }

    // Container
    private BorderPane container;
    private final ReadOnlyObjectWrapper<BorderPane> containerChange = new ReadOnlyObjectWrapper<>();

    public void setContainer(BorderPane container) {
        this.container = container;
        this.containerChange.set(this.container);
    }

    // Top
    private VBox top;
    private final ReadOnlyObjectWrapper<VBox> topChange = new ReadOnlyObjectWrapper<>();

    public void setTop(VBox top) {
        this.top = top;
        this.topChange.set(top);
    }

    // MenuBar
    private MenuBar menuBar;
    private final ReadOnlyObjectWrapper<MenuBar> menuBarChange = new ReadOnlyObjectWrapper<>();

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
        this.menuBarChange.set(menuBar);
    }

    // ToolBar
    private ToolBar toolBar;
    private final ReadOnlyObjectWrapper<ToolBar> toolBarChange = new ReadOnlyObjectWrapper<>();

    public void setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
        this.toolBarChange.set(toolBar);
    }

    // Center
    private BorderPane center;
    private final ReadOnlyObjectWrapper<BorderPane> centerChange = new ReadOnlyObjectWrapper<>();

    public void setCenter(BorderPane center) {
        this.center = center;
        this.centerChange.set(this.center);
    }

    // Bottom InfoBar
    private HBox bottomInfoBar;
    private final ReadOnlyObjectWrapper<HBox> bottomInfoBarChange = new ReadOnlyObjectWrapper<>();

    public void setBottomInfoBar(HBox bottomInfoBar) {
        this.bottomInfoBar = bottomInfoBar;
        this.bottomInfoBarChange.set(bottomInfoBar);
    }

    private Boolean borderBarsVisibility;
    private final ReadOnlyBooleanWrapper borderBarsVisibilityChange = new ReadOnlyBooleanWrapper();

    public void setBorderBarsVisibility(boolean borderBarsVisibility) {
        this.borderBarsVisibility = borderBarsVisibility;
        this.borderBarsVisibilityChange.set(borderBarsVisibility);
    }

    // Left BorderBar
    private VBox borderBarLeft;
    private final ReadOnlyObjectWrapper<VBox> borderBarLeftChange = new ReadOnlyObjectWrapper<>();

    public void setBorderBarLeft(VBox borderBarLeft) {
        this.borderBarLeft = borderBarLeft;
        this.borderBarLeftChange.set(borderBarLeft);
    }

    // Bottom BorderBar
    private HBox borderBarBottom;
    private final ReadOnlyObjectWrapper<HBox> borderBarBottomChange = new ReadOnlyObjectWrapper<>();

    public void setBorderBarBottom(HBox borderBarBottom) {
        this.borderBarBottom = borderBarBottom;
        this.borderBarBottomChange.set(borderBarBottom);
    }

    // Left JMEPlayComponent
    private JMEPlayComponent leftPlayComponent;
    private final ReadOnlyObjectWrapper<JMEPlayComponent> leftPlayComponentChange = new ReadOnlyObjectWrapper<>();

    public void setLeftPlayComponent(JMEPlayComponent leftPlayComponent) {
        this.leftPlayComponent = leftPlayComponent;
        this.leftPlayComponentChange.set(leftPlayComponent);
    }

    // Bottom JMEPlayComponent
    private JMEPlayComponent bottomPlayComponent;
    private final ReadOnlyObjectWrapper<JMEPlayComponent> bottomPlayComponentChange = new ReadOnlyObjectWrapper<>();

    public void setBottomPlayComponent(JMEPlayComponent bottomPlayComponent) {
        this.bottomPlayComponent = bottomPlayComponent;
        this.bottomPlayComponentChange.set(bottomPlayComponent);
    }

    // TabPane
    private TabPane tabPane;
    private final ReadOnlyObjectWrapper<Node> tabPaneChange = new ReadOnlyObjectWrapper<>();

    public void setTabPane(TabPane center) {
        this.tabPane = center;
        this.tabPaneChange.set(center);
    }

}
