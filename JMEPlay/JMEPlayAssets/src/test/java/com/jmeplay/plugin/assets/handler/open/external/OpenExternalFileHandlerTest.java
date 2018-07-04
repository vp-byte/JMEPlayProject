/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.open.external;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.handler.file.JMEPlayFileOpenerHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.open.OpenFileHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test to open files or folders from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see OpenExternalFileHandler
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                OpenFileHandler.class,
                JMEPlayFileOpenerHandler.class,
                OpenExternalFileHandler.class,
        })
public class OpenExternalFileHandlerTest {

    @Autowired
    private OpenExternalFileHandler openExternalFileHandler;

    /**
     * Supported extension types is file and file without extension only
     */
    @Test
    public void extension() {
        Assert.assertEquals(2, openExternalFileHandler.extension().size());
        Assert.assertEquals(JMEPlayFileHandler.file, openExternalFileHandler.extension().get(0));
        Assert.assertEquals(JMEPlayFileOpenerHandler.filenoextension, openExternalFileHandler.extension().get(1));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(openExternalFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(openExternalFileHandler.label());
        Assert.assertFalse(openExternalFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(openExternalFileHandler.image());
    }

}