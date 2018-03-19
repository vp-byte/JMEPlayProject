/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import javafx.scene.control.TreeView;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

/**
 * Interface to handle TreeView of JMEPlay
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public abstract class JMEPlayTreeView extends TreeView<Path> {
    @Setter
    @Getter
    private boolean selectAddedItem = false;

    public void setSelectAddedItem() {
        this.selectAddedItem = true;
    }
}
