/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.core.handler.file;

import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface to handle filetypes and folders
 * 
 * @author Vladimir Petrenko (vp-byte)
 */
public abstract class JMEPlayFileHandler<T> {

    public static final String any = "any";
    public static final String file = "file";
    public static final String folder = "folder";

    /**
     * Use any - for any type and folders
     * Use file - for any file type only
     * Use folder - for any folder
     * Use file extension - for specific file(txt for text file, jpg for image...)
     *
     * @return type of file types extension
     */
    public abstract List<String> filetypes();

    /**
     * Label value to represent handler in the GUI
     *
     * @return label to view in the GUI
     */
    public abstract String label();

    /**
     * Tooltip value to describe doing of the handler in the GUI
     *
     * @return description of the handler to view in the GUI
     */
    public abstract String tooltip();

    /**
     * Image or icon to represent action of the handler
     *
     * @return image to view in the GUI
     */
    public abstract ImageView image();

    /**
     * Execute action on the file
     *
     * @param path to the file
     */
    public abstract void handle(Path path, T source);
}
