/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

/**
 * Interface to handle console output for the editor
 *
 * @author  vp-byte (Vladimir Petrenko)
 */
public interface JMEPlayConsole {

	/**
	 * Write message to console
	 *
	 * @param type
	 * @param message simple string message
	 */
	void message(Type type, String message);

	/**
	 * Write exception directly to console
	 *
	 * @param exception all types
	 */
	void exception(Exception exception);

	public enum Type {
		ERROR, WARN, INFO, SUCCESS
	}

}
