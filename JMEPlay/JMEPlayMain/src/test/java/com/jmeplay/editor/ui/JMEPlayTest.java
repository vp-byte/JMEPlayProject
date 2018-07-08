/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.TestFxApplicationTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test start of application
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see JMEPlay
 */
public class JMEPlayTest extends TestFxApplicationTest {

    @Test
    public void testShow() {
        Assert.assertNotNull(super.getStage());
    }

}