package com.jmeplay.core.utils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test for {@link PathResolver}
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class PathResolverTest {

    @Test
    public void extension() {
        Path path = Paths.get("/home/best/text.txt");
        String extension = PathResolver.extension(path);
        Assert.assertEquals("txt", extension);
    }

    @Test
    public void filename() {
        Path path = Paths.get("/home/best/text.txt");
        String filename = PathResolver.name(path);
        Assert.assertEquals("text", filename);
    }
}