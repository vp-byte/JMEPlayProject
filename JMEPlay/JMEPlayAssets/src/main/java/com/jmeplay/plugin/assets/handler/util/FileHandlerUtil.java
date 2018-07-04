/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.util;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.utils.os.OSInfo;

import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for file handlers
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
            case LINUX:
                commands.add("xdg-open");
                break;
            default:
                return;
        }

        String url;
        try {
            url = path.toUri().toURL().toString();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("File " + path + " do not exist", ex);
        }

        commands.add(url);

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);

        try {
            processBuilder.start();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't open external file " + path, ex);
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
        for (int i = 0; i < builder.length(); i++) {
            buffer.put((byte) builder.charAt(i));
        }
        buffer.flip();
        return buffer;
    }

    /**
     * Converts byte buffer to string (utf-8)
     *
     * @param buffer as ByteBuffer
     * @return value as string
     */
    public static String fromByteBuffer(final ByteBuffer buffer) {
        return new String(buffer.array(), StandardCharsets.UTF_8);
    }

}
