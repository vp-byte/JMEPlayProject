/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.editor.JMEPlayEditorResources;

import javafx.beans.InvalidationListener;
import javafx.scene.Scene;

/**
 * Scene of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorScene {

    private InvalidationListener il = null;

    private final JMEPlayEditor jmePlayEditor;

    @Autowired
    public JMEPlayEditorScene(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    @PostConstruct
    public void init() {
        il = (in) -> {
            Scene scene = new Scene(jmePlayEditor.root());
            scene.getStylesheets().add(getClass().getResource(JMEPlayEditorResources.CSS).toExternalForm());
            jmePlayEditor.stage().setScene(scene);
            jmePlayEditor.setScene(scene);
            jmePlayEditor.rootChange().removeListener(il);
        };
        jmePlayEditor.rootChange().addListener(il);
    }

}
