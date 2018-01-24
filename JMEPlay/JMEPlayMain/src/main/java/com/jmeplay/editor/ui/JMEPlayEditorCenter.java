/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.InvalidationListener;
import javafx.scene.layout.BorderPane;

/**
 * Center of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorCenter {

	private InvalidationListener il = null;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@PostConstruct
	private void init() {
		il = (in) -> {
			BorderPane center = new BorderPane();
			jmePlayEditor.getContainer().setCenter(center);
			jmePlayEditor.setCenter(center);
			jmePlayEditor.getContainerChange().removeListener(il);
		};
		jmePlayEditor.getContainerChange().addListener(il);
	}

}
