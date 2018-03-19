/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Interface to get all JMEPlay editor components and nodes to use it application wide
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public interface JMEPlayGlobal {

    /**
     * Observe changes of asset folder path
     *
     * @return actual path of asset folder
     */
    ReadOnlyObjectProperty<Path> assetFolderChange();

    /**
     * JavaFX stage for whole application
     *
     * @return main stage
     */
    Stage stage();

    /**
     * Observe changes of stage
     *
     * @return actual main stage
     */
    ReadOnlyObjectProperty<Stage> stageChange();

    /**
     * JavaFX root component for whole application
     *
     * @return root component
     */
    Parent root();

    /**
     * Observe changes of root component
     *
     * @return actual root component
     */
    ReadOnlyObjectProperty<Parent> rootChange();

    /**
     * JavaFX scene for whole application
     *
     * @return scene
     */
    Scene scene();

    /**
     * Observe changes of scene
     *
     * @return actual scene
     */
    ReadOnlyObjectProperty<Scene> sceneChange();


    /**
     * Top tool bar for whole application
     *
     * @return tool bar
     */
    ToolBar toolBar();

    /**
     * Center tab pane for whole application
     *
     * @return tab pane
     */
    TabPane tabPane();

}
