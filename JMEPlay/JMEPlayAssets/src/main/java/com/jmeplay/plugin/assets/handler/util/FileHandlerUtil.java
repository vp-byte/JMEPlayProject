package com.jmeplay.plugin.assets.handler.util;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.List;

public class FileHandlerUtil {

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
