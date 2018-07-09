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
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * JMEPlayEditor component to handle all parts and nodes of the editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditor implements JMEPlayGlobal {

    // -- ASSET FOLDER
    private final ReadOnlyObjectWrapper<Path> assetfolderChange = new ReadOnlyObjectWrapper<>();

    /**
     * Set main folder for all assets
     *
     * @param assetfolder as path
     */
    public void setAssetFolder(Path assetfolder) {
        assetfolderChange.set(assetfolder);
    }

    /**
     * Implementation for asset folder change
     * {@link JMEPlayGlobal#assetFolderChange()}
     *
     * @return path to asset folder if changes
     */
    @Override
    public ReadOnlyObjectProperty<Path> assetFolderChange() {
        return assetfolderChange.getReadOnlyProperty();
    }

    // -- STAGE
    private Stage stage;
    private final ReadOnlyObjectWrapper<Stage> stageChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for main stage
     *
     * @param stage of application
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        stageChange.set(this.stage);
    }

    /**
     * Getter for main stage
     * {@link JMEPlayGlobal#stage()}
     *
     * @return main stage
     */
    @Override
    public Stage stage() {
        return stage;
    }

    /**
     * Implementation for stage change
     * {@link JMEPlayGlobal#sceneChange()}
     *
     * @return actual stage if changes
     */
    @Override
    public ReadOnlyObjectProperty<Stage> stageChange() {
        return stageChange.getReadOnlyProperty();
    }

    // --ROOT
    private Parent root;
    private final ReadOnlyObjectWrapper<Parent> rootChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for javafx root component
     *
     * @param root component
     */
    public void setRoot(Parent root) {
        this.root = root;
        rootChange.set(this.root);
    }

    /**
     * Getter for root component
     * {@link JMEPlayGlobal#root()}
     *
     * @return root component
     */
    @Override
    public Parent root() {
        return root;
    }

    /**
     * Implementation for root component change
     * {@link JMEPlayGlobal#rootChange()}
     *
     * @return actual root component if changes
     */
    @Override
    public ReadOnlyObjectProperty<Parent> rootChange() {
        return rootChange.getReadOnlyProperty();
    }

    // -- SCENE
    private Scene scene;
    private final ReadOnlyObjectWrapper<Scene> sceneChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for javafx scene
     *
     * @param scene for application
     */
    public void setScene(Scene scene) {
        this.scene = scene;
        sceneChange.set(this.scene);
    }

    /**
     * Getter for javafx scene
     * {@link JMEPlayGlobal#scene()}
     *
     * @return scene for application
     */
    @Override
    public Scene scene() {
        return scene;
    }

    /**
     * Implementation for scene change
     * {@link JMEPlayGlobal#sceneChange()}
     *
     * @return actual scene if changes
     */
    @Override
    public ReadOnlyObjectProperty<Scene> sceneChange() {
        return sceneChange.getReadOnlyProperty();
    }

    // -- CONTAINER
    private BorderPane container;
    private final ReadOnlyObjectWrapper<BorderPane> containerChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for main container
     * (1)_______________________
     * |                         |
     * |                         |
     * |                         |
     * |         PARENT          |
     * |                         |
     * |                         |
     * |                         |
     * |_________________________|
     *
     * @param container of application
     */
    public void setContainer(BorderPane container) {
        this.container = container;
        this.containerChange.set(this.container);
    }

    /**
     * Getter for main container
     *
     * @return container of application
     */
    public BorderPane container() {
        return container;
    }

    /**
     * Implementation for container change
     *
     * @return actual container if changes
     */
    public ReadOnlyObjectProperty<BorderPane> containerChange() {
        return containerChange.getReadOnlyProperty();
    }

    // -- TOP
    private VBox top;
    private final ReadOnlyObjectWrapper<VBox> topChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for top container
     * (2)_______________________
     * |                         | <
     * |           TOP           |  |
     * |_________________________|  |
     * |                         |  |
     * |                         |  |> PARENT
     * |                         |  |
     * |                         |  |
     * |                         |  |
     * |_________________________| <
     *
     * @param top container of application
     */
    public void setTop(VBox top) {
        this.top = top;
        this.topChange.set(top);
    }

    /**
     * Getter for top container
     *
     * @return top container of application
     */
    public VBox top() {
        return top;
    }

    /**
     * Implementation for top container change
     *
     * @return actual top container if changes
     */
    public ReadOnlyObjectProperty<VBox> topChange() {
        return topChange.getReadOnlyProperty();
    }

    // -- MENUBAR
    private MenuBar menuBar;
    private final ReadOnlyObjectWrapper<MenuBar> menuBarChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for menu bar in top container
     * (3)_______________________
     * |_________MENUBAR_________| <       <
     * |                         |  |> TOP  |
     * |_________________________| <        |
     * |                         |          |
     * |                         |          |> PARENT
     * |                         |          |
     * |                         |          |
     * |                         |          |
     * |_________________________|         <
     *
     * @param menuBar of top container
     */
    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
        menuBarChange.set(menuBar);
    }

    /**
     * Getter for top menu bar
     *
     * @return top menu bar of application
     */
    public MenuBar menuBar() {
        return menuBar;
    }

    /**
     * Implementation for top menu bar change
     *
     * @return actual top menu bar container if changes
     */
    public ReadOnlyObjectProperty<MenuBar> menuBarChange() {
        return menuBarChange.getReadOnlyProperty();
    }

    // TOOLBAR
    private ToolBar toolBar;
    private final ReadOnlyObjectWrapper<ToolBar> toolBarChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for tool bar in top container
     * (4)_______________________
     * |_________MENUBAR_________| <       <
     * |                         |  |> TOP  |
     * |_________TOOLBAR_________| <        |
     * |                         |          |
     * |                         |          |> PARENT
     * |                         |          |
     * |                         |          |
     * |                         |          |
     * |_________________________|         <
     *
     * @param toolBar of top container
     */
    public void setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
        toolBarChange.set(toolBar);
    }

    /**
     * Getter for top tool bar
     * {@link JMEPlayGlobal#toolBar()}
     *
     * @return top tool bar of application
     */
    @Override
    public ToolBar toolBar() {
        return toolBar;
    }

    /**
     * Implementation for top tool bar change
     *
     * @return actual top tool bar container if changes
     */
    public ReadOnlyObjectProperty<ToolBar> toolBarChange() {
        return toolBarChange.getReadOnlyProperty();
    }

    // CENTER
    private BorderPane center;
    private final ReadOnlyObjectWrapper<BorderPane> centerChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for center container
     * (5)_______________________
     * |_________MENUBAR_________| <       <
     * |                         |  |> TOP  |
     * |_________TOOLBAR_________| <        |
     * |                         |          |
     * |                         |          |> PARENT
     * |          CENTER         |          |
     * |                         |          |
     * |                         |          |
     * |_________________________|         <
     *
     * @param center container
     */
    public void setCenter(BorderPane center) {
        this.center = center;
        centerChange.set(this.center);
    }

    /**
     * Getter for center
     *
     * @return center component of application
     */
    public BorderPane center() {
        return center;
    }

    /**
     * Implementation for center change
     *
     * @return actual center component if changes
     */
    public ReadOnlyObjectProperty<BorderPane> centerChange() {
        return centerChange.getReadOnlyProperty();
    }

    // INFO BAR
    private HBox infoBar;
    private final ReadOnlyObjectWrapper<HBox> infoBarChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for info bar
     * (6)_______________________
     * |_________MENUBAR_________| <       <
     * |                         |  |> TOP  |
     * |_________TOOLBAR_________| <        |
     * |                         |          |
     * |                         |          |> PARENT
     * |          CENTER         |          |
     * |                         |          |
     * |_________________________|          |
     * |_________INFOBAR_________|         <
     *
     * @param infoBar container
     */
    public void setInfoBar(HBox infoBar) {
        this.infoBar = infoBar;
        infoBarChange.set(this.infoBar);
    }

    /**
     * Getter for info bar
     *
     * @return info bar of application
     */
    public HBox infoBar() {
        return this.infoBar;
    }

    /**
     * Implementation for info bar change
     *
     * @return actual info bar if changes
     */
    public ReadOnlyObjectProperty<HBox> infoBarChange() {
        return infoBarChange.getReadOnlyProperty();
    }

    private Boolean borderBarsVisibility;
    private final ReadOnlyBooleanWrapper borderBarsVisibilityChange = new ReadOnlyBooleanWrapper();

    /**
     * Setter for visibility of left and right border bars
     *
     * @param borderBarsVisibility of left and right
     */
    public void setBorderBarsVisibility(boolean borderBarsVisibility) {
        this.borderBarsVisibility = borderBarsVisibility;
        borderBarsVisibilityChange.set(borderBarsVisibility);
    }

    /**
     * Getter for visibility of left and right border bars
     *
     * @return visibility
     */
    public Boolean borderBarsVisibility() {
        return borderBarsVisibility;
    }

    /**
     * Implementation for left and right border bars visibility change
     *
     * @return actual visibility state if changes
     */
    public ReadOnlyBooleanProperty borderBarsVisibilityChange() {
        return borderBarsVisibilityChange.getReadOnlyProperty();
    }

    // LEFT BORDERBAR
    private VBox borderBarLeft;
    private final ReadOnlyObjectWrapper<VBox> borderBarLeftChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for left border bar
     * (7)_______________________
     * |_________MENUBAR_________| <       <
     * |                         |  |> TOP  |
     * |_________TOOLBAR_________| <        |
     * |L|                       |          |
     * |E|                       |          |> PARENT
     * |F|        CENTER         |          |
     * |T|                       |          |
     * |_|_______________________|          |
     * |_________INFOBAR_________|         <
     *
     * @param borderBarLeft of application
     */
    public void setBorderBarLeft(VBox borderBarLeft) {
        this.borderBarLeft = borderBarLeft;
        borderBarLeftChange.set(borderBarLeft);
    }

    /**
     * Getter for left border bar
     *
     * @return left border bar
     */
    public VBox borderBarLeft() {
        return borderBarLeft;
    }

    /**
     * Implementation for left border bar change
     *
     * @return actual left border bar if changes
     */
    public ReadOnlyObjectProperty<VBox> borderBarLeftChange() {
        return borderBarLeftChange.getReadOnlyProperty();
    }

    // BOTTOM BORDERBAR
    private HBox borderBarBottom;
    private final ReadOnlyObjectWrapper<HBox> borderBarBottomChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for bottom border bar
     * (8)_______________________
     * |_________MENUBAR_________| <       <
     * |                         |  |> TOP  |
     * |_________TOOLBAR_________| <        |
     * |L|                       |          |
     * |E|                       |          |> PARENT
     * |F|        CENTER         |          |
     * |T|__ __ __ __ __ __ __ __|          |
     * |_|________BOTTOM_________|          |
     * |_________INFOBAR_________|         <
     *
     * @param borderBarBottom of application
     */
    public void setBorderBarBottom(HBox borderBarBottom) {
        this.borderBarBottom = borderBarBottom;
        borderBarBottomChange.set(borderBarBottom);
    }

    /**
     * Getter for bottom border bar
     *
     * @return bottom border bar
     */
    public HBox borderBarBottom() {
        return borderBarBottom;
    }

    /**
     * Implementation for bottom border bar change
     *
     * @return actual bottom border bar if changes
     */
    public ReadOnlyObjectProperty<HBox> borderBarBottomChange() {
        return borderBarBottomChange.getReadOnlyProperty();
    }

    // LEFT JMEPLAYCOMPONENT
    private JMEPlayComponent leftPlayComponent;
    private final ReadOnlyObjectWrapper<JMEPlayComponent> leftPlayComponentChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for left region component
     * (9)_______________________
     * |_________MENUBAR_________| <          <
     * |                         |  |> TOP     |
     * |_________TOOLBAR_________| <           |
     * |L|  RE  |                | <           |
     * |E|  GI  |                |  |          |> PARENT
     * |F|  ON  |                |  |> CENTER  |
     * |T|_LEFT |_ __ __ __ __ __| <           |
     * |_|________BOTTOM_________|             |
     * |_________INFOBAR_________|            <
     *
     * @param leftPlayComponent of application
     */
    public void setLeftPlayComponent(JMEPlayComponent leftPlayComponent) {
        this.leftPlayComponent = leftPlayComponent;
        leftPlayComponentChange.set(leftPlayComponent);
    }

    /**
     * Getter for left region component
     *
     * @return left region
     */
    public JMEPlayComponent leftPlayComponent() {
        return leftPlayComponent;
    }

    /**
     * Implementation for left region component change
     *
     * @return actual left region component if changes
     */
    public ReadOnlyObjectProperty<JMEPlayComponent> leftPlayComponentChange() {
        return leftPlayComponentChange.getReadOnlyProperty();
    }

    // BOTTOM JMEPLAYCOMPONENT
    private JMEPlayComponent bottomPlayComponent;
    private final ReadOnlyObjectWrapper<JMEPlayComponent> bottomPlayComponentChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for bottom region component
     * (10)______________________
     * |_________MENUBAR_________| <          <
     * |                         |  |> TOP     |
     * |_________TOOLBAR_________| <           |
     * |L|  RE  |                | <           |
     * |E|  GI  |________________|  |          |> PARENT
     * |F|  ON  |    REGION      |  |> CENTER  |
     * |T|__ __ |_ __BOTTOM __ __| <           |
     * |_|________BOTTOM_________|             |
     * |_________INFOBAR_________|            <
     *
     * @param bottomPlayComponent of application
     */
    public void setBottomPlayComponent(JMEPlayComponent bottomPlayComponent) {
        this.bottomPlayComponent = bottomPlayComponent;
        this.bottomPlayComponentChange.set(bottomPlayComponent);
    }

    /**
     * Getter for bottom region component
     *
     * @return bottom region
     */
    public JMEPlayComponent bottomPlayComponent() {
        return bottomPlayComponent;
    }

    /**
     * Implementation for bottom region component change
     *
     * @return actual bottom region component if changes
     */
    public ReadOnlyObjectProperty<JMEPlayComponent> bottomPlayComponentChange() {
        return bottomPlayComponentChange.getReadOnlyProperty();
    }

    // TABPANE
    private TabPane tabPane;
    private final ReadOnlyObjectWrapper<TabPane> tabPaneChange = new ReadOnlyObjectWrapper<>();

    /**
     * Setter for center tab pane region component
     * (10)______________________
     * |_________MENUBAR_________| <          <
     * |                         |  |> TOP     |
     * |_________TOOLBAR_________| <           |
     * |L|  RE  |     TABPANE       | <           |
     * |E|  GI  |________________|  |          |> PARENT
     * |F|  ON  |    REGION      |  |> CENTER  |
     * |T|__ __ |_ __BOTTOM __ __| <           |
     * |_|________BOTTOM_________|             |
     * |_________INFOBAR_________|            <
     *
     * @param center tab pane of application
     */
    public void setTabPane(TabPane center) {
        this.tabPane = center;
        this.tabPaneChange.set(center);
    }

    /**
     * Getter for center tab pane
     * {@link JMEPlayGlobal#tabPane()}
     *
     * @return center tab pane
     */
    @Override
    public TabPane tabPane() {
        return tabPane;
    }

    /**
     * Implementation for center tab pane change
     *
     * @return actual center tab pane if changes
     */
    public ReadOnlyObjectProperty<TabPane> tabPaneChange() {
        return tabPaneChange.getReadOnlyProperty();
    }
}
