/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import javafx.beans.InvalidationListener;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ToolBar of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopToolBar {

    private InvalidationListener il = null;

    private final JMEPlayEditor jmePlayEditor;

    @Autowired
    public JMEPlayEditorTopToolBar(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    @PostConstruct
    public void init() {
        il = (in) -> {
            ToolBar toolBar = new ToolBar(new Button("New"), new Button("Open"), new Button("Save"), new Separator(Orientation.VERTICAL), new Button("Run"));
            jmePlayEditor.top().getChildren().add(1, toolBar);
            jmePlayEditor.setToolBar(toolBar);
            jmePlayEditor.topChange().removeListener(il);
        };
        jmePlayEditor.topChange().addListener(il);
    }

}
