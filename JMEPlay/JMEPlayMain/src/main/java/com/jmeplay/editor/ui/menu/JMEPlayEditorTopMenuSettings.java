/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.menu;

import com.jmeplay.editor.JMEPlayEditorLocalization;
import javafx.scene.control.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Settings menu of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuSettings {

    private Menu menuSettings;

    private final JMEPlayEditorLocalization jmePlayEditorLocalization;

    /**
     * Constructor to create settings item in top menu
     *
     * @param jmePlayEditorLocalization localization of menu
     */
    @Autowired
    public JMEPlayEditorTopMenuSettings(JMEPlayEditorLocalization jmePlayEditorLocalization) {
        this.jmePlayEditorLocalization = jmePlayEditorLocalization;
    }

    /**
     * Initialize settings menu
     */
    @PostConstruct
    private void init() {
        menuSettings = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_SETTINGS));
    }

    /**
     * Top settings menu
     *
     * @return settings menu
     */
    public Menu menu() {
        return menuSettings;
    }

}
