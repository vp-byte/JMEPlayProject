/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor;

import com.jmeplay.core.JMEPlayGlobalResources;

/**
 * All resources for JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class JMEPlayEditorResources {

    public final static String SETTINGSFILE = JMEPlayGlobalResources.SETTINGSFOLDER + "EditorSettings.xml";

    // CSS
    public final static String CSS = JMEPlayGlobalResources.CSS + "JMEPlayEditor.css";

    // ICONS
    public final static String ICON_DISABLE = JMEPlayGlobalResources.BASE + "editor/icons/disable.svg";
    public final static String ICON_ENABLE = JMEPlayGlobalResources.BASE + "editor/icons/enable.svg";

    // KEYS FOR SETTINGS
    public final static String STAGE_X = "StageX";
    public final static Double STAGE_X_DEFAULT = null;
    public final static String STAGE_Y = "StageY";
    public final static Double STAGE_Y_DEFAULT = null;
    public final static String STAGE_MIN_WIDTH = "StageMinWidth";
    public final static Double STAGE_MIN_WIDTH_DEFAULT = 800.0;
    public final static String STAGE_WIDTH = "StageWidth";
    public final static String STAGE_MIN_HEIGHT = "StageMinHeight";
    public final static Double STAGE_MIN_HEIGHT_DEFAULT = 600.0;
    public final static String STAGE_HEIGHT = "StageHeight";
    public final static String MAXIMIZED = "Maximized";
    public final static Boolean MAXIMIZED_DEFAULT = true;

    public final static String BORDER_BARS_VISIBLE = "BorderBarsVisible";
    public final static Boolean BORDER_BARS_VISIBLE_DEFAULT = true;
    public final static String LEFT_BAR_VISIBLE = "LeftVisible";
    public final static Boolean LEFT_BAR_VISIBLE_DEFAULT = true;
    public final static String BOTTOM_BAR_VISIBLE = "BottomVisible";
    public final static Boolean BOTTOM_BAR_VISIBLE_DEFAULT = true;
    public final static String LEFT_DIVIDER_POSITION = "LeftDividerPosition";
    public final static String LEFT_SELECTED_COMPONENT = "LeftSelectedComponent";
    public final static Double LEFT_DIVIDER_POSITION_DEFAULT = 0.2;
    public final static String BOTTOM_DIVIDER_POSITION = "BottomDividerPosition";
    public final static Double BOTTOM_DIVIDER_POSITION_DEFAULT = 0.8;
    public final static String BOTTOM_SELECTED_COMPONENT = "BottomSelectedComponent";

}
