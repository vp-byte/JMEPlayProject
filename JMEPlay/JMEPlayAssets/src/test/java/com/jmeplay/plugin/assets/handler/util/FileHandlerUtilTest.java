/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.util;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Test to user file handler utils
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see FileHandlerUtil
 */
public class FileHandlerUtilTest {

    private final String testString = "My simple test string";

    @Test
    public void openInSystem() {
        Process process = FileHandlerUtil.openInSystem(Paths.get(System.getProperty("user.home")));
        Assert.assertNotNull(process);
        process.destroy();
        Assert.assertFalse(process.isAlive());
    }

    @Test
    public void toByteBuffer() {
        final ByteBuffer buffer = ByteBuffer.wrap(testString.getBytes(StandardCharsets.US_ASCII));
        Assert.assertEquals(buffer, FileHandlerUtil.toByteBuffer(testString));
    }

    @Test
    public void fromByteBuffer() {
        final ByteBuffer buffer = ByteBuffer.wrap(testString.getBytes(StandardCharsets.US_ASCII));
        Assert.assertEquals(testString, FileHandlerUtil.fromByteBuffer(buffer));
    }

}