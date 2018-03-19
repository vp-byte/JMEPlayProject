/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Abstract class to define new component in editor on left or bottom side
 */
public abstract class JMEPlayComponent {
    private int priority = Integer.MAX_VALUE;
    private Align align = Align.LEFT;

    /**
     * @return priority to place label of component in view
     */
    public Integer priority() {
        return priority;
    }

    /**
     * Set priority to manage label position in view
     *
     * @param priority of component
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return alignment of component left or bottom
     */
    public Align align() {
        return align;
    }

    /**
     * Change alignment of component
     *
     * @param align in view
     */
    public void setAlignment(Align align) {
        this.align = align;
    }

    /**
     * @return name of editor component
     */
    public abstract String name();

    /**
     * @return description of editor component
     */
    public abstract String description();

    /**
     * Label to add in border bar
     *
     * @return designed label
     */
    public abstract Label label();

    /**
     * Main view of component as node
     *
     * @return view as node
     */
    public abstract Node component();

    public enum Align {
        LEFT, BOTTOM
    }
}
