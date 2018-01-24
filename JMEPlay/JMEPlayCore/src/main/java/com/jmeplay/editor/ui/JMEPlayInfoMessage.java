/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

/**
 * Interface to handle info output for the editor (bottom info bar)
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public interface JMEPlayInfoMessage {

	/**
	 * Set info message of the editor
	 *
	 * @param text as string
	 */
	void message(String text);

}
