/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.plugin.console;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.utils.SettingsLoader;

/**
 * All console settings
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleSettings extends SettingsLoader {

	@Autowired
	private JMEPlayGlobalSettings jmePlayGlobalSettings;

	public JMEPlayConsoleSettings() {
		super();
	}

	/**
	 * Load settings
	 */
	@PostConstruct
	private void init() {
		loadSettings(JMEPlayConsoleResources.SETTINGSFILE);
	}

	/**
	 * Write settings
	 */
	@PreDestroy
	private void destroy() {
		writeSettings();
	}

	public boolean writeExceptions() {
		return value(JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS, JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS_DEFAULT);
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

}
