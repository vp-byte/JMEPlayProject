/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.core.utils;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Opener for files or folders with system defined programs
 *
 * @author Vladimir Petrenko (vp-byte)
 */
public class SystemOpener {


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

}
