/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if (filename.contains(".")) {
                return filename.substring(0, filename.lastIndexOf("."));
            }
        }
        return "" + path.getFileName();
    }


    public static Path reindexName(final Path path) {
        Path newPath = path;
        if (Files.exists(newPath)) {
            Path parent = path.getParent();
            String name = name(path);
            String extension = extension(path);
            String point = Files.isRegularFile(path) && !extension.isEmpty() ? "." : "";


            Matcher matcher = Pattern.compile("-\\d").matcher(name);
            Integer start = null;
            Integer end = null;
            while (matcher.find()) {
                start = matcher.start() + 1;
                end = matcher.end();
            }
            if (start != null) {
                Integer index = Integer.parseInt(name.substring(start, end));
                name = name.substring(0, start);
                name += (index + 1);
            } else {
                name += "-1";
            }
            newPath = Paths.get(parent.toString(), name + point + extension);
            if (Files.exists(newPath)) {
                newPath = reindexName(newPath);
            }
        }
        return newPath;
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