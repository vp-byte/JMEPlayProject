/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import javafx.beans.InvalidationListener;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Center of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorCenter {

    private InvalidationListener il = null;

    private final JMEPlayEditor jmePlayEditor;

    @Autowired
    public JMEPlayEditorCenter(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    @PostConstruct
    private void init() {
        il = (in) -> {
            BorderPane center = new BorderPane();
            jmePlayEditor.container().setCenter(center);
            jmePlayEditor.setCenter(center);
            jmePlayEditor.containerChange().removeListener(il);
        };
        jmePlayEditor.containerChange().addListener(il);
    }

}
