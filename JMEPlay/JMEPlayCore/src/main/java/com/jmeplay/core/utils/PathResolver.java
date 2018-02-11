/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core.utils;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Resolver name and extension by path
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class PathResolver {

    /**
     * Extension from path (last point as separator)
     *
     * @param path of file
     * @return extension of file (.svg, .jpg, other..., empty string if directory)
     */
    public static String extension(Path path) {
        if (path == null) {
            return null;
        }
        String filename = path.getFileName().toString();
        int lastIndexOfDot = filename.lastIndexOf(".");
        if (lastIndexOfDot > 0) {
            return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        }
        return "";
    }

    /**
     * Name for file or directory without extension from path (last point as separator for file)
     *
     * @param path of file
     * @return extension of file (.svg, .jpg, other...)
     */
    public static String name(Path path) {
        if (Files.isRegularFile(path)) {
            String filename = "" + path.getFileName();
            return filename.substring(0, filename.lastIndexOf("."));
        }
        return "" + path.getName(path.getNameCount() - 1);
    }

    /**
     * Check file name for validity
     *
     * @param name of file
     * @return validity
     */
    public static boolean nameinvalid(String name) {
        return !name.matches("[-_.A-Za-z0-9][^<>?\":|\\/*]*");
    }

}