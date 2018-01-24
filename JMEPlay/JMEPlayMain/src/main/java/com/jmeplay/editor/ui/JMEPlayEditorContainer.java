/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;

/**
 * Container of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorContainer {

	private InvalidationListener il = null;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@PostConstruct
	private void init() {
		il = (in) -> {
			BorderPane container = new BorderPane();
			container.prefHeightProperty().bind(jmePlayEditor.getStage().getScene().heightProperty());
			container.prefWidthProperty().bind(jmePlayEditor.getStage().getScene().widthProperty());
			((Group) jmePlayEditor.getRoot()).getChildren().add(container);
			jmePlayEditor.setContainer(container);
			jmePlayEditor.getSceneChange().removeListener(il);
		};
		jmePlayEditor.getSceneChange().addListener(il);
	}
	
}
