/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Simple file visitor to delete all files
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class DeleteFileVisitor extends SimpleFileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(DeleteFileVisitor.class.getName());

    /**
     * Visit directory to delete it
     */
    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
        Files.delete(dir);
        logger.trace("Directory " + dir + " deleted");
        return FileVisitResult.CONTINUE;
    }

    /**
     * Visit file to delete it
     */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        logger.trace("File " + file + " deleted");
        return FileVisitResult.CONTINUE;
    }

}
