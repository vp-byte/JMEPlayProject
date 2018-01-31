/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core.handler.file;

import javafx.scene.control.MenuItem;

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
     * Menu or MenuItem to handle filetypes and folders
     *
     * @param source for MenuItem
     * @return created menu item
     */
    public abstract MenuItem menu(T source);

}
