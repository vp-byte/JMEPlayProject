/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.utils.SettingsLoader;

/**
 * All settings for JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorSettings extends SettingsLoader {

	@Autowired
	private JMEPlayGlobalSettings jmePlayGlobalSettings;

	public JMEPlayEditorSettings() {
		super();
	}

	@PostConstruct
	private void init() {
		loadSettings(JMEPlayEditorResources.SETTINGSFILE);
	}

	@PreDestroy
	private void destroy() {
		writeSettings();
	}

	public String rootFolder() {
		return jmePlayGlobalSettings.rootFolder();
	}

	public int iconSize() {
		return jmePlayGlobalSettings.iconSize();
	}

	public int iconSizeBar() {
		return jmePlayGlobalSettings.iconSizeBar();
	}

	public int iconSpacing() {
		return jmePlayGlobalSettings.iconSpacing();
	}

	public boolean borderBarsVisible() {
		return value(JMEPlayEditorResources.BORDER_BARS_VISIBLE, JMEPlayEditorResources.BORDER_BARS_VISIBLE_DEFAULT);
	}

}
