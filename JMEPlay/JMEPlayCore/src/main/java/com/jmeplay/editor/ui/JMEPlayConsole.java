/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui;

/**
 * Interface to handle console output for the editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public interface JMEPlayConsole {

    /**
     * Write message to console
     *
     * @param type    of message
     * @param message simple string message
     */
    void message(Type type, String message);

    /**
     * Write exception directly to console
     *
     * @param exception all types
     */
    void exception(Exception exception);

    enum Type {
        ERROR, WARN, INFO, SUCCESS
    }

}
