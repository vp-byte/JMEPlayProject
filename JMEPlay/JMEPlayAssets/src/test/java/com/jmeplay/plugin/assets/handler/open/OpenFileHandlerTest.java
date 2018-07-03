/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.open;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.handler.file.JMEPlayFileOpenerHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.open.OpenFileHandler;
import com.jmeplay.plugin.assets.handler.open.external.OpenExternalFileHandler;
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
 * @see OpenFileHandler
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
public class OpenFileHandlerTest {

    @Autowired
    private OpenFileHandler openFileHandler;

    /**
     * Supported file type is file only
     */
    @Test
    public void filetypes() {
        Assert.assertEquals(1, openFileHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.file, openFileHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(openFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(openFileHandler.label());
        Assert.assertFalse(openFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(openFileHandler.image());
    }

}