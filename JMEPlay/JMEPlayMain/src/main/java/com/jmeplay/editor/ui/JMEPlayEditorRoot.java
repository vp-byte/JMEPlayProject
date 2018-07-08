/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Root component of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorRoot {

    private InvalidationListener il = null;
    private final JMEPlayEditor jmePlayEditor;

    /**
     * Root of editor
     *
     * @param jmePlayEditor main container
     */
    @Autowired
    public JMEPlayEditorRoot(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Initialize and set root
     * {@link JMEPlayEditor#setRoot(Parent)}
     * {@link JMEPlayEditor#root()}
     * {@link JMEPlayEditor#rootChange()}
     */
    @PostConstruct
    public void init() {
        il = (in) -> {
            Group root = new Group();
            root.setId("root");
            jmePlayEditor.setRoot(root);
            jmePlayEditor.stageChange().removeListener(il);
        };
        jmePlayEditor.stageChange().addListener(il);
    }

}
