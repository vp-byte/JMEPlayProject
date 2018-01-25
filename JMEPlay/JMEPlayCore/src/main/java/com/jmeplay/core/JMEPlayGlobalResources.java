/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core;

/**
 * All resources for global settings
 * 
 * @author Vladimir Petrenko (vp-byte)
 */
public class JMEPlayGlobalResources {

	// File for global settigs
	public final static String SETTINGSFOLDER = System.getProperty("user.home") + "/.jmeplay/";
	public final static String SETTINGSFILE = SETTINGSFOLDER + "GlobalSettings.xml";

	// Path to default resources
	public final static String BASE = "/com/jmeplay/";
	public final static String CSS = BASE + "css/";
	public final static String ICONS = BASE + "icons/";

	// Root folder for assets
	public final static String ROOTFOLDER = "rootfolder";

	// Other global settings for whole application
	public final static String ICONSIZE = "iconsize";
	public final static int ICONSIZE_DEFAULT = 16;
	public final static String ICONSIZE_BAR = "iconsizebar";
	public final static int ICONSIZE_BAR_DEFAULT = 24;
	public final static String SPACING = "spacing";
	public final static int SPACING_DEFAULT = 2;

}
