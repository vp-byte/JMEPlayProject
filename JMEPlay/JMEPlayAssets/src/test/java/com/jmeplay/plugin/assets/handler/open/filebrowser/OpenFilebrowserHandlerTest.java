/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.open.filebrowser;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.handler.file.JMEPlayFileOpenerHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test to open files or folders in file explorer from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see OpenFilebrowserHandler
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                JMEPlayFileOpenerHandler.class,
                OpenFilebrowserHandler.class,
        })
public class OpenFilebrowserHandlerTest {

    @Autowired
    private OpenFilebrowserHandler openFilebrowserHandler;

    /**
     * Supported file type is any
     */
    @Test
    public void filetypes() {
        Assert.assertEquals(1, openFilebrowserHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.any, openFilebrowserHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(openFilebrowserHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(openFilebrowserHandler.label());
        Assert.assertFalse(openFilebrowserHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(openFilebrowserHandler.image());
    }
}