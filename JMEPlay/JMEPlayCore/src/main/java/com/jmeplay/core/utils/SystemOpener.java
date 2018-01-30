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

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static void openExtern(Path path) {
        final List<String> commands = new ArrayList<>();
        if (isMac()) {
            commands.add("open");
        } else if (isWindows()) {
            commands.add("cmd");
            commands.add("/c");
            commands.add("start");
        } else if (isUnix()) {
            commands.add("xdg-open");
        }

        if (commands.isEmpty()) {
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

    /**
     * @return true if actual operating system windows
     */
    private static boolean isWindows() {
        return (OS.contains("win"));
    }

    /**
     * @return true if actual operating system mac
     */
    private static boolean isMac() {
        return (OS.contains("mac"));
    }

    /**
     * @return true if actual operating system linux or unix
     */
    private static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
}
