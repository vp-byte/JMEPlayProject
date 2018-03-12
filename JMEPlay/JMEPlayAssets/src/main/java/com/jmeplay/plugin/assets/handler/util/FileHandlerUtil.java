/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler.util;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.utils.OSInfo;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util to help file handlers
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class FileHandlerUtil {

    public static void openExtern(Path path) {
        final List<String> commands = new ArrayList<>();

        switch (OSInfo.OS()) {
            case MAC:
                commands.add("open");
                break;
            case WINDOWS:
                commands.add("cmd");
                commands.add("/c");
                commands.add("start");
                break;
            case UNIX:
            case POSIX_UNIX:
                commands.add("xdg-open");
                break;
            default:
                return;
        }

        String url;
        try {
            url = path.toUri().toURL().toString();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("File " + path + " do not exist");
        }

        commands.add(url);

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);


        try {
            processBuilder.start();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't open external file " + path);
        }
    }

    public static ByteBuffer toByteBufferCopy(final List<Path> paths) {
        return toByteBuffer(new StringBuilder(JMEPlayClipboardFormat.COPY + "\n"), paths);
    }

    public static ByteBuffer toByteBufferCut(final List<Path> paths) {
        return toByteBuffer(new StringBuilder(JMEPlayClipboardFormat.CUT + "\n"), paths);
    }

    private static ByteBuffer toByteBuffer(final StringBuilder builder, final List<Path> paths) {
        paths.forEach(path -> builder.append(path.toUri().toASCIIString()).append('\n'));
        builder.delete(builder.length() - 1, builder.length());
        final ByteBuffer buffer = ByteBuffer.allocate(builder.length());
        for (int i = 0, length = builder.length(); i < length; i++) {
            buffer.put((byte) builder.charAt(i));
        }
        buffer.flip();
        return buffer;
    }

    public static String fromByteBuffer(ByteBuffer buffer) {
        try {
            return new String(buffer.array(), "ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
