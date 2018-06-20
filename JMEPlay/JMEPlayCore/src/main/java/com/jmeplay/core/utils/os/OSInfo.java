/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils.os;

import java.util.Locale;

/**
 * Info about used operating system
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class OSInfo {

    /**
     * Type of running operating system
     */
    public enum OSType {
        WINDOWS,
        UNIX,
        POSIX_UNIX,
        MAC,
        OTHER
    }

    private static OSType os;

    static {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (osName.contains("windows")) {
            os = OSType.WINDOWS;
        } else if (osName.contains("linux")
                || osName.contains("mpe/ix")
                || osName.contains("freebsd")
                || osName.contains("irix")
                || osName.contains("digital unix")
                || osName.contains("unix")) {
            os = OSType.UNIX;
        } else if (osName.contains("mac os")) {
            os = OSType.MAC;
        } else if (osName.contains("sun os")
                || osName.contains("sunos")
                || osName.contains("solaris")) {
            os = OSType.POSIX_UNIX;
        } else if (osName.contains("hp-ux")
                || osName.contains("aix")) {
            os = OSType.POSIX_UNIX;
        } else {
            os = OSType.OTHER;
        }
    }

    /**
     * Type of actual OS
     * {@link OSType}
     *
     * @return type of OS
     */
    public static OSType OS() {
        return os;
    }
}