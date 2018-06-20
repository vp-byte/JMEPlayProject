/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Test for {@link PathResolver}
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class PathResolverTest {

    private static String home = System.getProperty("user.home");
    private static String filename;
    private static String extension;
    private static Path path;

    /**
     * Create text file
     */
    @Before
    public void createFile() {
        filename = UUID.randomUUID().toString();
        extension = "txt";
        path = Paths.get(home, filename + "." + extension);
        try {
            Files.createFile(path);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Delete test file
     */
    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Resolve extension
     */
    @Test
    public void extension() {
        Assert.assertEquals(extension, PathResolver.extension(path));
    }

    /**
     * Resolve filename
     */
    @Test
    public void name() {
        Assert.assertEquals(filename, PathResolver.name(path));
    }

    /**
     * Reindex simple(no index in name) filename
     */
    @Test
    public void reindexNameNoIndex() {
        try {
            Path filePath = Paths.get(home, "namewithnoindex.txt");
            Files.createFile(filePath);
            Path reindexedPath = PathResolver.reindexName(filePath);
            Assert.assertEquals(Paths.get(home, "namewithnoindex-1.txt"), reindexedPath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Reindex indexed filename
     */
    @Test
    public void reindexNameWithIndex() {
        try {
            Path filePath = Paths.get(home, "namewithnoindex-1.txt");
            Files.createFile(filePath);
            Path reindexedPath = PathResolver.reindexName(filePath);
            Assert.assertEquals(Paths.get(home, "namewithnoindex-2.txt"), reindexedPath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Reindex simple(no index in name) filename without extension
     */
    @Test
    public void reindexNameNoIndexAndExtension() {
        try {
            Path filePath = Paths.get(home, "namewithnoindex");
            Files.createFile(filePath);
            Path reindexedPath = PathResolver.reindexName(filePath);
            Assert.assertEquals(Paths.get(home, "namewithnoindex-1"), reindexedPath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Reindex indexed filename with number in name
     */
    @Test
    public void reindexNameWithIndexAndNumbers() {
        try {
            Path filePath = Paths.get(home, "namewithnoindex-111-222-1.txt");
            Files.createFile(filePath);
            Path reindexedPath = PathResolver.reindexName(filePath);
            Assert.assertEquals(Paths.get(home, "namewithnoindex-111-222-2.txt"), reindexedPath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
}