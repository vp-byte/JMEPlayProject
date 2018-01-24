/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.InvalidationListener;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

/**
 * ToolBar of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopToolBar {

	private InvalidationListener il = null;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@PostConstruct
	public void init() {
		il = (in) -> {
			ToolBar toolBar = new ToolBar(new Button("New"), new Button("Open"), new Button("Save"), new Separator(Orientation.VERTICAL), new Button("Run"));
			jmePlayEditor.getTop().getChildren().add(1, toolBar);
			jmePlayEditor.setToolBar(toolBar);
			jmePlayEditor.getTopChange().removeListener(il);
		};
		jmePlayEditor.getTopChange().addListener(il);
	}

}
