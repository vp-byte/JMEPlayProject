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

    public static DataFormat JMEPLAY_FILES() {
        final String jmeplayId = "JMEPlay.files";
        DataFormat df = DataFormat.lookupMimeType(jmeplayId);
        return df != null ? df : new DataFormat(jmeplayId);
    }

    public static DataFormat JAVA_FILES(){
        final String javafilesId = "application/x-java-file-list";
        DataFormat df = DataFormat.lookupMimeType(javafilesId);
        return df != null ? df : new DataFormat(javafilesId);
    }

    public static DataFormat GNOME_FILES() {
        final String gnomeId = "x-special/gnome-copied-files";
        DataFormat df = DataFormat.lookupMimeType(gnomeId);
        return df != null ? df : new DataFormat(gnomeId);
    }

    public static final String COPY = "copy";

    public static final String CUT = "cut";
}
