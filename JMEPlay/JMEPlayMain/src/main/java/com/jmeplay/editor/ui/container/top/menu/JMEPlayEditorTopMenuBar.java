/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.top.menu;

import com.jmeplay.editor.ui.JMEPlayEditor;
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

    /**
     * Constructor to create top menu bar of editor
     *
     * @param jmePlayEditor                to setup top menu bar
     * @param jmePlayEditorTopMenuFile     file menu
     * @param jmePlayEditorTopMenuSettings settings menu
     * @param jmePlayEditorTopMenuHelp     help menu
     */
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

    /**
     * Initialize top menu for editor
     * {@link JMEPlayEditor#setMenuBar(MenuBar)}
     * {@link JMEPlayEditor#menuBar()}
     * {@link JMEPlayEditor#menuBarChange()}
     */
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
