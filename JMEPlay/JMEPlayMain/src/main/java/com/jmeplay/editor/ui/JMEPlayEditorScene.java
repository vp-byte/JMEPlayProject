/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
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

    /**
     * Scene of editor
     *
     * @param jmePlayEditor main container
     */
    @Autowired
    public JMEPlayEditorScene(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Initialize and set root
     * {@link JMEPlayEditor#setScene(Scene)}
     * {@link JMEPlayEditor#scene()}
     * {@link JMEPlayEditor#sceneChange()}
     */
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
