/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Bottom InfoBar of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorBottomInfoBar implements JMEPlayInfoMessage {

    private int size;

    private Label modeSwitcher;
    private Label infoLabel;
    private HBox bottomInfoBar;

    private final JMEPlayEditorSettings jmePlayEditorSettings;
    private final JMEPlayEditor jmePlayEditor;

    @Autowired
    public JMEPlayEditorBottomInfoBar(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
        this.jmePlayEditor = jmePlayEditor;
    }

    @PostConstruct
    private void init() {
        size = jmePlayEditorSettings.iconSizeBar();
        jmePlayEditor.setBorderBarsVisibility(jmePlayEditorSettings.value(JMEPlayEditorResources.BORDER_BARS_VISIBLE, JMEPlayEditorResources.BORDER_BARS_VISIBLE_DEFAULT));
        jmePlayEditor.containerChange().addListener((in) -> {
            jmePlayEditor.borderBarsVisibilityChange().addListener((observable, oldValue, newValue) -> {
                jmePlayEditorSettings.setValue(JMEPlayEditorResources.BORDER_BARS_VISIBLE, newValue);
                modeSwitcher.setGraphic(modeSwitcherImage(newValue));
            });

            initModeSwitcher();
            initInfoLabel();
            initBottomInfoBar();
            jmePlayEditor.container().setBottom(bottomInfoBar);
            jmePlayEditor.setBottomInfoBar(bottomInfoBar);
        });
    }

    private void initModeSwitcher() {
        modeSwitcher = new Label();
        modeSwitcher.setGraphic(modeSwitcherImage(true));
        modeSwitcher.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> jmePlayEditor.setBorderBarsVisibility(!jmePlayEditor.borderBarsVisibility()));
    }

    private ImageView modeSwitcherImage(boolean visibility) {
        String imagePath = JMEPlayEditorResources.ICON_DISABLE;
        if (!visibility) {
            imagePath = JMEPlayEditorResources.ICON_ENABLE;
        }
        return ImageLoader.imageView(this.getClass(), imagePath, size - 4, size - 4);
    }

    private void initInfoLabel() {
        infoLabel = new Label();
        infoLabel.getStyleClass().add("infobar-infolabel");
        message("Info message");
    }

    private void initBottomInfoBar() {
        bottomInfoBar = new HBox();
        bottomInfoBar.getStyleClass().add("infobar-bottom-box");
        bottomInfoBar.setMinHeight(size);
        bottomInfoBar.setMaxHeight(size);
        bottomInfoBar.setAlignment(Pos.CENTER_LEFT);
        bottomInfoBar.getChildren().addAll(modeSwitcher, infoLabel);
    }

    @Override
    public void message(String text) {
        this.infoLabel.setText(text);
    }
}
