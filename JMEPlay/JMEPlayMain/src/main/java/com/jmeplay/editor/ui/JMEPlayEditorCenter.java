/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
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

    /**
     * Constructor to create center view of editor
     *
     * @param jmePlayEditor to setup center view
     */
    @Autowired
    public JMEPlayEditorCenter(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Initialize center view for editor
     * {@link JMEPlayEditor#setCenter(BorderPane)}
     * {@link JMEPlayEditor#center()}
     * {@link JMEPlayEditor#centerChange()}
     */
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
