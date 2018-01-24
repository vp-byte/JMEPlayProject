/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.editor.ui.menu.JMEPlayEditorTopMenuFile;
import com.jmeplay.editor.ui.menu.JMEPlayEditorTopMenuHelp;
import com.jmeplay.editor.ui.menu.JMEPlayEditorTopMenuSettings;

import javafx.beans.InvalidationListener;
import javafx.scene.control.MenuBar;

/**
 * MenuBar of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuBar {

	private InvalidationListener il = null;
	private MenuBar menuBar;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@Autowired
	private JMEPlayEditorTopMenuFile jmePlayEditorTopMenuFile;

	@Autowired
	private JMEPlayEditorTopMenuSettings jmePlayEditorTopMenuSettings;

	@Autowired
	private JMEPlayEditorTopMenuHelp jmePlayEditorTopMenuHelp;

	@PostConstruct
	public void init() {
		il = (in) -> {
			menuBar = new MenuBar();
			menuBar.getMenus().add(jmePlayEditorTopMenuFile.menu());
			menuBar.getMenus().add(jmePlayEditorTopMenuSettings.menu());
			menuBar.getMenus().add(jmePlayEditorTopMenuHelp.menu());
			jmePlayEditor.getTop().getChildren().add(0, menuBar);
			jmePlayEditor.setMenuBar(menuBar);
			jmePlayEditor.getTopChange().removeListener(il);
		};
		jmePlayEditor.getTopChange().addListener(il);
	}

}
