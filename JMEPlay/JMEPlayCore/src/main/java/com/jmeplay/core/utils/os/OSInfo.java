/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils.os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Info about used operating system
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class OSInfo {

    private static final Logger logger = LoggerFactory.getLogger(OSInfo.class.getName());

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
                || osName.contains("unix")
                || osName.contains("sun os")
                || osName.contains("sunos")
                || osName.contains("solaris")
                || osName.contains("hp-ux")
                || osName.contains("aix")) {
            os = OSType.LINUX;
        } else if (osName.contains("mac os")) {
            os = OSType.MAC;
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
        logger.trace("OS from {}: {}", OSInfo.class, os);
        return os;
    }

}