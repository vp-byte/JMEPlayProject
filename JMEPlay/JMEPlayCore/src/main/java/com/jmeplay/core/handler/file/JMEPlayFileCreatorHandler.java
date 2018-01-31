/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core.handler.file;

import javafx.scene.control.MenuItem;

import java.util.List;

/**
 * Interface to create varias file types
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public interface JMEPlayFileCreatorHandler<T> {

    /**
     * Menu or MenuItem to handle filetypes and folders
     *
     * @param source for MenuItem
     * @return created menu item
     */
    public abstract MenuItem menu(T source);

}
