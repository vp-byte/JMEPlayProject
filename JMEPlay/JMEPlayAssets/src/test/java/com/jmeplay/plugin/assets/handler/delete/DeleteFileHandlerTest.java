/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Test to delete files from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                DeleteFileHandler.class,
                DeleteFileHandlerDialog.class,
                DeleteFileVisitor.class
        })
public class DeleteFileHandlerTest extends ApplicationTest {

    @Autowired
    private DeleteFileHandler deleteFileHandler;
    private static List<Path> paths;

    /**
     * Create text file
     */
    @BeforeClass
    public static void createFile() {
        paths = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            paths.add(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
        }
        try {
            for (Path path : paths) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Supported file type is any
     */
    @Test
    public void filetypes() {
        Assert.assertEquals(1, deleteFileHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.any, deleteFileHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(deleteFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(deleteFileHandler.label());
        Assert.assertFalse(deleteFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(deleteFileHandler.image());
    }

    /**
     * Test delete files from filesystem
     */
    @Test
    public void deletePath() {
        paths.forEach(path -> {
            Assert.assertTrue(Files.exists(path));
            try {
                deleteFileHandler.deletePath(path);
            } catch (IOException e) {
                Assert.fail("Delete " + path + " fail: " + e.getMessage());
            }
            Assert.assertFalse(Files.exists(path));
        });
    }

}