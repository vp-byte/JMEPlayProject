/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.create;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileCreatorHandler;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.create.folder.CreateFolderHandler;
import com.jmeplay.plugin.assets.handler.create.folder.CreateFolderHandlerDialog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Test to copy files from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see CreateFileHandler
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                CreateFileHandler.class,
                JMEPlayFileCreatorHandler.class,
                CreateFolderHandler.class,
                CreateFolderHandlerDialog.class
        })
public class CreateFileHandlerTest extends ApplicationTest {

    @Autowired
    private CreateFileHandler createFileHandler;

    /**
     * Supported file type is any
     */
    @Test
    public void filetypes() {
        Assert.assertEquals(1, createFileHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.any, createFileHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(createFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(createFileHandler.label());
        Assert.assertFalse(createFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(createFileHandler.image());
    }

}