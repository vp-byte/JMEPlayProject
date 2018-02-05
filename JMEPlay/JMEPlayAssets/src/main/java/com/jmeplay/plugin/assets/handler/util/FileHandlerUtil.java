package com.jmeplay.plugin.assets.handler.util;

import com.jmeplay.core.utils.OSInfo;

import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public static ByteBuffer toByteBuffer(final List<Path> paths) {

        final StringBuilder builder = new StringBuilder("copy\n");

        paths.forEach(path ->
                builder.append(path.toUri().toASCIIString()).append('\n'));

        builder.delete(builder.length() - 1, builder.length());

        final ByteBuffer buffer = ByteBuffer.allocate(builder.length());

        for (int i = 0, length = builder.length(); i < length; i++) {
            buffer.put((byte) builder.charAt(i));
        }

        buffer.flip();
        return buffer;
    }
}
