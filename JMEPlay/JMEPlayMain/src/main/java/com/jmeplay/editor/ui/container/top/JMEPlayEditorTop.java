/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.top;

import com.jmeplay.editor.ui.JMEPlayEditor;
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

    private final JMEPlayEditor jmePlayEditor;

    /**
     * Constructor to create top view of editor
     *
     * @param jmePlayEditor to setup top view
     */
    @Autowired
    public JMEPlayEditorTop(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Initialize top view for editor
     * {@link JMEPlayEditor#setTop(VBox)}
     * {@link JMEPlayEditor#top()}
     * {@link JMEPlayEditor#topChange()}
     */
    @PostConstruct
    private void init() {
        il = (in) -> {
            VBox top = new VBox();
            top.setId("top");
            jmePlayEditor.container().setTop(top);
            jmePlayEditor.setTop(top);
            jmePlayEditor.containerChange().removeListener(il);
        };
        jmePlayEditor.containerChange().addListener(il);
    }

}
