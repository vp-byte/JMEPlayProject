/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.SettingsLoader;

/**
 * All global settings
 *
 * @author Vladimir Petrenko (vp-byte)
 */
@Component
public class JMEPlayGlobalSettings extends SettingsLoader {

	public JMEPlayGlobalSettings() {
		super();
	}

	@PostConstruct
	private void init() {
		loadSettings(JMEPlayGlobalResources.SETTINGSFILE);
	}

	@PreDestroy
	private void destroy() {
		writeSettings();
	}

	public String rootFolder() {
		return value(JMEPlayGlobalResources.ROOTFOLDER, System.getProperty("user.home"));
	}

	public int iconSize() {
		return value(JMEPlayGlobalResources.ICONSIZE, JMEPlayGlobalResources.ICONSIZE_DEFAULT);
	}

	public int iconSizeBar() {
		return value(JMEPlayGlobalResources.ICONSIZE_BAR, JMEPlayGlobalResources.ICONSIZE_BAR_DEFAULT);
	}

	public int iconSpacing() {
		return value(JMEPlayGlobalResources.SPACING, JMEPlayGlobalResources.SPACING_DEFAULT);
	}

}
