/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.util;

import com.jmeplay.core.utils.os.OSInfo;

import java.net.MalformedURLException;
import java.nio.Buffer;
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

    /**
     * Open path with system program, folder open with file explorer
     *
     * @param path to file or folder
     */
    public static Process openInSystem(Path path) {
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
                return null;
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
            return processBuilder.start();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't open external file " + path, ex);
        }
    }

    /**
     * Create buffer from params clipboard format as string and paths as list
     *
     * @param clipboardFormat as string
     * @param paths           list of paths
     * @return initialized buffer
     */
    public static Buffer toBuffer(final String clipboardFormat, final List<Path> paths) {
        final StringBuilder builder = new StringBuilder(clipboardFormat + "\n");
        paths.forEach(path -> builder.append(path.toUri().toASCIIString()).append('\n'));
        builder.delete(builder.length() - 1, builder.length());
        final ByteBuffer buffer = toByteBuffer(builder.toString());
        return buffer.flip();
    }

    /**
     * Converts string to byte buffer (US_ASCII)
     *
     * @param data as String
     * @return buffer as ByteBuffer
     */
    static ByteBuffer toByteBuffer(String data) {
        return ByteBuffer.wrap(data.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * Converts byte buffer to string (US_ASCII)
     *
     * @param buffer as ByteBuffer
     * @return value as String
     */
    public static String fromByteBuffer(final ByteBuffer buffer) {
        return new String(buffer.array(), StandardCharsets.US_ASCII);
    }

}
