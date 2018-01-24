/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui.menu;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.editor.JMEPlayEditorLocalization;

import javafx.scene.control.Menu;

/**
 * Settings menu of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuSettings {

	private Menu menuSettings;

	@Autowired
	private JMEPlayEditorLocalization jmePlayEditorLocalization;

	@PostConstruct
	private void init() {
		menuSettings = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_MENU_SETTINGS));
	}

	public Menu menu() {
		return menuSettings;
	}

}
