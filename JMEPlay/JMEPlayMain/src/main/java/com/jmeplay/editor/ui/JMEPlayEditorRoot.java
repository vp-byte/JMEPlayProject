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

/**
 * Root component of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorRoot {

	private InvalidationListener il = null;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@PostConstruct
	public void init() {
		il = (in) -> {
			jmePlayEditor.setRoot(new Group());
			jmePlayEditor.getStageChange().removeListener(il);
		};
		jmePlayEditor.getStageChange().addListener(il);
	}

}
