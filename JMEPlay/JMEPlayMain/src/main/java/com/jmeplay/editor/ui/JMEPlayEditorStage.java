/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import javafx.beans.InvalidationListener;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Setup stage with title and all other varies options
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorStage {

    private InvalidationListener il = null;
    private Stage stage;

    @Value("${application.name}")
    private String applicationName;

    @Value("${jme.version}")
    private String jmeVersion;

    private final JMEPlayEditorSettings jmePlayEditorSettings;
    private final JMEPlayEditor jmePlayEditor;

    @Autowired
    public JMEPlayEditorStage(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
        this.jmePlayEditor = jmePlayEditor;
    }

    @PostConstruct
    public void init() {
        il = (in) -> {
            this.stage = jmePlayEditor.stage();
            stage();
            jmePlayEditor.stageChange().removeListener(il);
        };
        jmePlayEditor.stageChange().addListener(il);
    }

    private void stage() {
        title();
        minHeight();
        height();
        minWidth();
        width();
        maximized();

        stage.setScene(stage.getScene());
        stage.show();

        stageY();
        stageX();
    }

    private void title() {
        stage.setTitle(applicationName + " (" + jmeVersion + ")");
    }

    private void minHeight() {
        Double stageMinHeight = jmePlayEditorSettings.value(JMEPlayEditorResources.STAGE_MIN_HEIGHT, JMEPlayEditorResources.STAGE_MIN_HEIGHT_DEFAULT);
        stage.setMinHeight(stageMinHeight);
    }

    private void height() {
        Double stageHeight = jmePlayEditorSettings.value(JMEPlayEditorResources.STAGE_HEIGHT, JMEPlayEditorResources.STAGE_MIN_HEIGHT_DEFAULT);
        stage.setHeight(stageHeight);
        stage.heightProperty().addListener((observable, oldValue, newValue) -> jmePlayEditorSettings.setValue(JMEPlayEditorResources.STAGE_HEIGHT, newValue.doubleValue()));
    }

    private void minWidth() {
        Double stageMinWidth = jmePlayEditorSettings.value(JMEPlayEditorResources.STAGE_MIN_WIDTH, JMEPlayEditorResources.STAGE_MIN_WIDTH_DEFAULT);
        stage.setMinWidth(stageMinWidth);
    }

    private void width() {
        Double stageWidth = jmePlayEditorSettings.value(JMEPlayEditorResources.STAGE_WIDTH, JMEPlayEditorResources.STAGE_MIN_WIDTH_DEFAULT);
        stage.setWidth(stageWidth);
        stage.widthProperty().addListener((observable, oldValue, newValue) -> jmePlayEditorSettings.setValue(JMEPlayEditorResources.STAGE_WIDTH, newValue.doubleValue()));
    }

    private void maximized() {
        stage.setMaximized(jmePlayEditorSettings.value(JMEPlayEditorResources.MAXIMIZED, JMEPlayEditorResources.MAXIMIZED_DEFAULT));
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> jmePlayEditorSettings.setValue(JMEPlayEditorResources.MAXIMIZED, newValue));
    }

    private void stageY() {
        Double stageY = jmePlayEditorSettings.value(JMEPlayEditorResources.STAGE_Y, JMEPlayEditorResources.STAGE_Y_DEFAULT);
        if (stageY != null) {
            stage.setY(stageY);
        }
        stage.yProperty().addListener((observable, oldValue, newValue) -> {
            if (!stage.isMaximized()) {
                jmePlayEditorSettings.setValue(JMEPlayEditorResources.STAGE_Y, newValue.doubleValue());
            }
        });
    }

    private void stageX() {
        Double stageX = jmePlayEditorSettings.value(JMEPlayEditorResources.STAGE_X, JMEPlayEditorResources.STAGE_X_DEFAULT);
        if (stageX != null) {
            double screensWidth = Screen.getScreens().stream().mapToDouble(screen -> screen.getBounds().getWidth()).sum();
            double stageXWidth = stageX + stage.getMinWidth();
            if (stageXWidth <= screensWidth) {
                stage.setX(stageX);
            }
        }
        stage.xProperty().addListener((observable, oldValue, newValue) -> {
            if (!stage.isMaximized()) {
                jmePlayEditorSettings.setValue(JMEPlayEditorResources.STAGE_X, newValue.doubleValue());
            }
        });
    }

}
