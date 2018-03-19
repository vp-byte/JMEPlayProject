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
 * Help menu of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuHelp {

    private Menu menuHelp;

    private final JMEPlayEditorLocalization jmePlayEditorLocalization;

    /**
     * Constructor to create help item in top menu
     *
     * @param jmePlayEditorLocalization localization of menu
     */
    @Autowired
    public JMEPlayEditorTopMenuHelp(JMEPlayEditorLocalization jmePlayEditorLocalization) {
        this.jmePlayEditorLocalization = jmePlayEditorLocalization;
    }

    /**
     * Initialize help menu
     */
    @PostConstruct
    private void init() {
        menuHelp = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_HELP));
    }

    /**
     * Top help menu
     *
     * @return help menu
     */
    public Menu menu() {
        return menuHelp;
    }

}
