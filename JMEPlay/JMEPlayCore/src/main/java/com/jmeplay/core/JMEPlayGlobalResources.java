/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
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
    final static String SETTINGSFILE = SETTINGSFOLDER + "GlobalSettings.xml";

    // Path to default resources
    public final static String BASE = "/com/jmeplay/";
    public final static String CSS = BASE + "css/";
    public final static String ICONS = BASE + "icons/";

    // Root folder for assets
    public final static String ROOTFOLDER = "rootfolder";

    // Other global settings for whole application
    final static String ICONSIZE = "iconsize";
    final static int ICONSIZE_DEFAULT = 16;
    final static String ICONSIZE_BAR = "iconsizebar";
    final static int ICONSIZE_BAR_DEFAULT = 24;
    final static String SPACING = "spacing";
    final static int SPACING_DEFAULT = 2;

}
