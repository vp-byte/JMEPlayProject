/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.core.utils.os;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Test for {@link OSInfo}
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class OSInfoTest {

    /**
     * Test find right os name
     */
    @Test
    public void OS() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (osName.contains("windows")) {
            Assert.assertEquals(OSType.WINDOWS, OSInfo.OS());
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
            Assert.assertEquals(OSType.LINUX, OSInfo.OS());
        } else if (osName.contains("mac os")) {
            Assert.assertEquals(OSType.MAC, OSInfo.OS());
        } else {
            Assert.assertEquals(OSType.OTHER, OSInfo.OS());
        }
    }

}