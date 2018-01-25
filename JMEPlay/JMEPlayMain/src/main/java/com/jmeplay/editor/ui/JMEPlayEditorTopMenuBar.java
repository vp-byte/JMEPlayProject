/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.ui.menu.JMEPlayEditorTopMenuFile;
import com.jmeplay.editor.ui.menu.JMEPlayEditorTopMenuHelp;
import com.jmeplay.editor.ui.menu.JMEPlayEditorTopMenuSettings;
import javafx.beans.InvalidationListener;
import javafx.scene.control.MenuBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * MenuBar of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuBar {

    private InvalidationListener il = null;
    private MenuBar menuBar;

    private final JMEPlayEditor jmePlayEditor;
    private final JMEPlayEditorTopMenuFile jmePlayEditorTopMenuFile;
    private final JMEPlayEditorTopMenuSettings jmePlayEditorTopMenuSettings;
    private final JMEPlayEditorTopMenuHelp jmePlayEditorTopMenuHelp;

    @Autowired
    public JMEPlayEditorTopMenuBar(JMEPlayEditor jmePlayEditor,
                                   JMEPlayEditorTopMenuFile jmePlayEditorTopMenuFile,
                                   JMEPlayEditorTopMenuSettings jmePlayEditorTopMenuSettings,
                                   JMEPlayEditorTopMenuHelp jmePlayEditorTopMenuHelp) {
        this.jmePlayEditor = jmePlayEditor;
        this.jmePlayEditorTopMenuFile = jmePlayEditorTopMenuFile;
        this.jmePlayEditorTopMenuSettings = jmePlayEditorTopMenuSettings;
        this.jmePlayEditorTopMenuHelp = jmePlayEditorTopMenuHelp;
    }

    @PostConstruct
    public void init() {
        il = (in) -> {
            menuBar = new MenuBar();
            menuBar.getMenus().add(jmePlayEditorTopMenuFile.menu());
            menuBar.getMenus().add(jmePlayEditorTopMenuSettings.menu());
            menuBar.getMenus().add(jmePlayEditorTopMenuHelp.menu());
            jmePlayEditor.top().getChildren().add(0, menuBar);
            jmePlayEditor.setMenuBar(menuBar);
            jmePlayEditor.topChange().removeListener(il);
        };
        jmePlayEditor.topChange().addListener(il);
    }

}
