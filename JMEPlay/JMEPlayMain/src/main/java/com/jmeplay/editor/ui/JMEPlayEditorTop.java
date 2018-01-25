/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javafx.beans.InvalidationListener;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Top of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTop {

    private InvalidationListener il = null;

    private JMEPlayEditor jmePlayEditor;

    @Autowired
    public JMEPlayEditorTop(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    @PostConstruct
    private void init() {
        il = (in) -> {
            VBox top = new VBox();
            jmePlayEditor.container().setTop(top);
            jmePlayEditor.setTop(top);
            jmePlayEditor.containerChange().removeListener(il);
        };
        jmePlayEditor.containerChange().addListener(il);
    }

}
