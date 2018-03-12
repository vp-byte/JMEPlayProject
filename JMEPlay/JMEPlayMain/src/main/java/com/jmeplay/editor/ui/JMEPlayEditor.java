/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * JMEPlayEditor component to handle all parts and nodes of the editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditor implements JMEPlayGlobal {

    private Path assetfolder;
    private final ReadOnlyObjectWrapper<Path> assetfolderChange = new ReadOnlyObjectWrapper<>();

    public void setAssetFolder(Path assetfolder) {
        this.assetfolder = assetfolder;
        assetfolderChange.set(this.assetfolder);
    }

    @Override
    public ReadOnlyObjectProperty<Path> assetFolderChange() {
        return assetfolderChange.getReadOnlyProperty();
    }

    // Stage
    private Stage stage;
    private final ReadOnlyObjectWrapper<Stage> stageChange = new ReadOnlyObjectWrapper<>();

    public void setStage(Stage stage) {
        this.stage = stage;
        stageChange.set(this.stage);
    }

    public Stage stage() {
        return stage;
    }

    public ReadOnlyObjectProperty<Stage> stageChange() {
        return stageChange.getReadOnlyProperty();
    }

    // Root
    private Parent root;
    private final ReadOnlyObjectWrapper<Parent> rootChange = new ReadOnlyObjectWrapper<>();

    public void setRoot(Parent root) {
        this.root = root;
        rootChange.set(this.root);
    }

    public Parent root() {
        return root;
    }

    public ReadOnlyObjectProperty<Parent> rootChange() {
        return rootChange.getReadOnlyProperty();
    }

    // Scene
    private Scene scene;
    private final ReadOnlyObjectWrapper<Scene> sceneChange = new ReadOnlyObjectWrapper<>();

    public void setScene(Scene scene) {
        this.scene = scene;
        sceneChange.set(this.scene);
    }

    public Scene scene() {
        return scene;
    }

    @Override
    public ReadOnlyObjectProperty<Scene> sceneChange() {
        return sceneChange.getReadOnlyProperty();
    }

    // Container
    private BorderPane container;
    private final ReadOnlyObjectWrapper<BorderPane> containerChange = new ReadOnlyObjectWrapper<>();

    public void setContainer(BorderPane container) {
        this.container = container;
        this.containerChange.set(this.container);
    }

    public BorderPane container() {
        return container;
    }

    public ReadOnlyObjectProperty<BorderPane> containerChange() {
        return containerChange.getReadOnlyProperty();
    }

    // Top
    private VBox top;
    private final ReadOnlyObjectWrapper<VBox> topChange = new ReadOnlyObjectWrapper<>();

    public void setTop(VBox top) {
        this.top = top;
        this.topChange.set(top);
    }

    public VBox top() {
        return top;
    }

    public ReadOnlyObjectProperty<VBox> topChange() {
        return topChange.getReadOnlyProperty();
    }

    // MenuBar
    private MenuBar menuBar;
    private final ReadOnlyObjectWrapper<MenuBar> menuBarChange = new ReadOnlyObjectWrapper<>();

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
        menuBarChange.set(menuBar);
    }

    public MenuBar menuBar() {
        return menuBar;
    }

    public ReadOnlyObjectProperty<MenuBar> menuBarChange() {
        return menuBarChange.getReadOnlyProperty();
    }

    // ToolBar
    private ToolBar toolBar;
    private final ReadOnlyObjectWrapper<ToolBar> toolBarChange = new ReadOnlyObjectWrapper<>();

    public void setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
        toolBarChange.set(toolBar);
    }

    public ToolBar toolBar() {
        return toolBar;
    }

    public ReadOnlyObjectProperty<ToolBar> toolBarChange() {
        return toolBarChange.getReadOnlyProperty();
    }

    // Center
    private BorderPane center;
    private final ReadOnlyObjectWrapper<BorderPane> centerChange = new ReadOnlyObjectWrapper<>();

    public void setCenter(BorderPane center) {
        this.center = center;
        centerChange.set(this.center);
    }

    public BorderPane center() {
        return center;
    }

    public ReadOnlyObjectProperty<BorderPane> centerChange() {
        return centerChange.getReadOnlyProperty();
    }

    // Bottom InfoBar
    private HBox bottomInfoBar;
    private final ReadOnlyObjectWrapper<HBox> bottomInfoBarChange = new ReadOnlyObjectWrapper<>();

    public void setBottomInfoBar(HBox bottomInfoBar) {
        this.bottomInfoBar = bottomInfoBar;
        bottomInfoBarChange.set(bottomInfoBar);
    }

    public HBox bottomInfoBar() {
        return borderBarBottom;
    }

    public ReadOnlyObjectProperty<HBox> bottomInfoBarChange() {
        return bottomInfoBarChange.getReadOnlyProperty();
    }

    private Boolean borderBarsVisibility;
    private final ReadOnlyBooleanWrapper borderBarsVisibilityChange = new ReadOnlyBooleanWrapper();

    public void setBorderBarsVisibility(boolean borderBarsVisibility) {
        this.borderBarsVisibility = borderBarsVisibility;
        borderBarsVisibilityChange.set(borderBarsVisibility);
    }

    public Boolean borderBarsVisibility() {
        return borderBarsVisibility;
    }

    public ReadOnlyBooleanProperty borderBarsVisibilityChange() {
        return borderBarsVisibilityChange.getReadOnlyProperty();
    }

    // Left BorderBar
    private VBox borderBarLeft;
    private final ReadOnlyObjectWrapper<VBox> borderBarLeftChange = new ReadOnlyObjectWrapper<>();

    public void setBorderBarLeft(VBox borderBarLeft) {
        this.borderBarLeft = borderBarLeft;
        borderBarLeftChange.set(borderBarLeft);
    }

    public VBox borderBarLeft() {
        return borderBarLeft;
    }

    public ReadOnlyObjectProperty<VBox> borderBarLeftChange() {
        return borderBarLeftChange.getReadOnlyProperty();
    }

    // Bottom BorderBar
    private HBox borderBarBottom;
    private final ReadOnlyObjectWrapper<HBox> borderBarBottomChange = new ReadOnlyObjectWrapper<>();

    public void setBorderBarBottom(HBox borderBarBottom) {
        this.borderBarBottom = borderBarBottom;
        borderBarBottomChange.set(borderBarBottom);
    }

    public HBox borderBarBottom() {
        return borderBarBottom;
    }

    public ReadOnlyObjectProperty<HBox> borderBarBottomChange() {
        return borderBarBottomChange.getReadOnlyProperty();
    }

    // Left JMEPlayComponent
    private JMEPlayComponent leftPlayComponent;
    private final ReadOnlyObjectWrapper<JMEPlayComponent> leftPlayComponentChange = new ReadOnlyObjectWrapper<>();

    public void setLeftPlayComponent(JMEPlayComponent leftPlayComponent) {
        this.leftPlayComponent = leftPlayComponent;
        leftPlayComponentChange.set(leftPlayComponent);
    }

    public JMEPlayComponent leftPlayComponent() {
        return leftPlayComponent;
    }

    public ReadOnlyObjectProperty<JMEPlayComponent> leftPlayComponentChange() {
        return leftPlayComponentChange.getReadOnlyProperty();
    }

    // Bottom JMEPlayComponent
    private JMEPlayComponent bottomPlayComponent;
    private final ReadOnlyObjectWrapper<JMEPlayComponent> bottomPlayComponentChange = new ReadOnlyObjectWrapper<>();

    public void setBottomPlayComponent(JMEPlayComponent bottomPlayComponent) {
        this.bottomPlayComponent = bottomPlayComponent;
        this.bottomPlayComponentChange.set(bottomPlayComponent);
    }

    public JMEPlayComponent bottomPlayComponent() {
        return bottomPlayComponent;
    }

    public ReadOnlyObjectProperty<JMEPlayComponent> bottomPlayComponentChange() {
        return bottomPlayComponentChange.getReadOnlyProperty();
    }

    // TabPane
    private TabPane tabPane;
    private final ReadOnlyObjectWrapper<TabPane> tabPaneChange = new ReadOnlyObjectWrapper<>();

    public void setTabPane(TabPane center) {
        this.tabPane = center;
        this.tabPaneChange.set(center);
    }

    @Override
    public TabPane tabPane() {
        return tabPane;
    }

    public ReadOnlyObjectProperty<TabPane> tabPaneChange() {
        return tabPaneChange.getReadOnlyProperty();
    }
}
