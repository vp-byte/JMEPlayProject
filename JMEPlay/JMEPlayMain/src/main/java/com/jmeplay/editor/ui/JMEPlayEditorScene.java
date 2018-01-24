/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.editor.JMEPlayEditorResources;

import javafx.beans.InvalidationListener;
import javafx.scene.Scene;

/**
 * Scene of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorScene {

	private InvalidationListener il = null;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@PostConstruct
	public void init() {
		il = (in) -> {
			Scene scene = new Scene(jmePlayEditor.getRoot());
			scene.getStylesheets().add(getClass().getResource(JMEPlayEditorResources.CSS).toExternalForm());
			jmePlayEditor.getStage().setScene(scene);
			jmePlayEditor.setScene(scene);
			jmePlayEditor.getRootChange().removeListener(il);
		};
		jmePlayEditor.getRootChange().addListener(il);
	}

}
