/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.core.utils;

import java.nio.file.Path;

/**
 * Resolver for file extension from path
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class ExtensionResolver {

	/**
	 * Extension from path (last point as separator)
	 * 
	 * @param path of file
	 * @return extension of file (.svg, .jpg, other...)
	 */
	public static String resolve(Path path) {
		if (path == null) {
			return null;
		}
		String filename = path.getFileName().toString();
		int lastIndexOfDot = filename.lastIndexOf(".");
		if (lastIndexOfDot > 0) {
			return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		}
		return null;
	}

}