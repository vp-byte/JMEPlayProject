/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core.handler.file;

import javafx.scene.input.DataFormat;

/**
 * Represents formats for clipboard
 */
public class JMEPlayClipboardFormat {

    public static final DataFormat JMEPLAY_FILES = new DataFormat("JMEPlay.files");

    public static final DataFormat GNOME_FILES = new DataFormat("x-special/gnome-copied-files");
}
