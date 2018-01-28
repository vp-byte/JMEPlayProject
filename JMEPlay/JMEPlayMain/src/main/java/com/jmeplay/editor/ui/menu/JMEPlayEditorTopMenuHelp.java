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
 * Help menu of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuHelp {

    private Menu menuHelp;

    private final JMEPlayEditorLocalization jmePlayEditorLocalization;

    @Autowired
    public JMEPlayEditorTopMenuHelp(JMEPlayEditorLocalization jmePlayEditorLocalization) {
        this.jmePlayEditorLocalization = jmePlayEditorLocalization;
    }

    @PostConstruct
    private void init() {
        menuHelp = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_HELP));
    }

    public Menu menu() {
        return menuHelp;
    }

}
