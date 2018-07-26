/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(PathResolver.class.getName());

    /**
     * Extension from path (last point as separator)
     *
     * @param path of file
     * @return extension of file (.svg, .jpg, other..., empty string if directory)
     */
    public static String extension(Path path) {
        String extension = null;
        if (path != null) {
            if (Files.isRegularFile(path)) {
                String filename = path.getFileName().toString();
                int lastIndexOfDot = filename.lastIndexOf(".");
                if (lastIndexOfDot > 1) {
                    extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
                    logger.trace("Extract extension " + extension + " from path " + path);
                } else {
                    logger.trace("Can't extract extension from path/file with no extension: " + path);
                    extension = "";
                }
            } else {
                logger.trace("Can't extract extension from folder: " + path);
                extension = "";
            }
        } else {
            logger.trace("Can't extract extension, path is null");
        }
        return extension;
    }

    /**
     * Name for file or directory without extension from path (last point as separator for file)
     *
     * @param path of file
     * @return extension of file (.svg, .jpg, other...)
     */
    public static String name(Path path) {
        String filename = "";
        if (Files.isRegularFile(path)) {
            filename += path.getFileName();
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf("."));
            }
        } else {
            filename += path.getFileName();
        }
        logger.trace("Extract filename " + filename + " from path/file: " + path);
        return filename;
    }


    /**
     * Get next indexed filename from actual filename
     *
     * @param path of file
     * @return new path of reindexed filename
     */
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
    public static boolean isNameInvalid(String name) {
        //noinspection RegExpRedundantEscape
        return !name.matches("[-_.A-Za-z0-9][^<>?\":|\\/*]*");
    }

}