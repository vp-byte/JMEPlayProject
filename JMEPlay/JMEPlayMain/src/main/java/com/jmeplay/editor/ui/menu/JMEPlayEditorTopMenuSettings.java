/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
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

    @Autowired
    public JMEPlayEditorTopMenuSettings(JMEPlayEditorLocalization jmePlayEditorLocalization) {
        this.jmePlayEditorLocalization = jmePlayEditorLocalization;
    }

    @PostConstruct
    private void init() {
        menuSettings = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_MENU_SETTINGS));
    }

    public Menu menu() {
        return menuSettings;
    }

}
