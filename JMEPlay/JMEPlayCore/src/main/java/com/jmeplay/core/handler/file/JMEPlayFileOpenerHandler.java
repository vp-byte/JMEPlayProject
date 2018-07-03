/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.handler.file;

import javafx.scene.control.MenuItem;

import java.util.List;

/**
 * Interface to open files by type / extension
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public interface JMEPlayFileOpenerHandler<T> {

    String filenoextension = "filenoextension";

    /**
     * Use file extension - for specific file(txt for text file, jpg for image...)
     *
     * @return type of file types extension
     */
    List<String> extension();

    /**
     * MenuItem to handle extension and folders
     *
     * @param source for MenuItem
     * @return created menu item
     */
    MenuItem menu(T source);
}
