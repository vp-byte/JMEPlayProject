/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.handler.file;

import javafx.scene.input.DataFormat;

/**
 * Represents formats for clipboard
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class JMEPlayClipboardFormat {

    public static final DataFormat JMEPLAY_FILES = new DataFormat("JMEPlay.files");

    public static final DataFormat GNOME_FILES = new DataFormat("x-special/gnome-copied-files");

    public static final String COPY = "copy";

    public static final String CUT = "cut";
}
