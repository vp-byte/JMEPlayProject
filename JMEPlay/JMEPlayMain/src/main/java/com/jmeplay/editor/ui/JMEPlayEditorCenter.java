/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
            jmePlayEditor.getContainer().setCenter(center);
            jmePlayEditor.setCenter(center);
            jmePlayEditor.getContainerChange().removeListener(il);
        };
        jmePlayEditor.getContainerChange().addListener(il);
    }

}
