/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;

/**
 * Container of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorContainer {

    private InvalidationListener il = null;
    private final JMEPlayEditor jmePlayEditor;

    /**
     * Main container of editor
     *
     * @param jmePlayEditor parent view
     */
    @Autowired
    public JMEPlayEditorContainer(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Initialize and set container
     * {@link JMEPlayEditor#setContainer(BorderPane)}
     * {@link JMEPlayEditor#container()}
     * {@link JMEPlayEditor#containerChange()}
     */
    @PostConstruct
    private void init() {
        il = (in) -> {
            BorderPane container = new BorderPane();
            container.prefHeightProperty().bind(jmePlayEditor.stage().getScene().heightProperty());
            container.prefWidthProperty().bind(jmePlayEditor.stage().getScene().widthProperty());
            ((Group) jmePlayEditor.root()).getChildren().add(container);
            jmePlayEditor.setContainer(container);
            jmePlayEditor.sceneChange().removeListener(il);
        };
        jmePlayEditor.sceneChange().addListener(il);
    }

}
