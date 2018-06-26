/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.rename;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Test to rename files from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                RenameFileHandler.class,
                RenameFileHandlerDialog.class
        })
public class RenameFileHandlerTest {

    @Autowired
    private RenameFileHandler renameFileHandler;
    private static Map<Path, Path> paths;

    /**
     * Create test files
     */
    @BeforeClass
    public static void createFile() {
        paths = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            paths.put(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()), Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
        }
        try {
            for (Map.Entry<Path, Path> entry : paths.entrySet()) {
                Files.createFile(entry.getKey());
            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Delete test files
     */
    @After
    public void deleteFile() {
        try {
            for (Map.Entry<Path, Path> entry : paths.entrySet()) {
                Files.deleteIfExists(entry.getValue());
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
        Assert.assertEquals(1, renameFileHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.any, renameFileHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(renameFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(renameFileHandler.label());
        Assert.assertFalse(renameFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(renameFileHandler.image());
    }

    @Test
    public void renamePath() {
        for (Map.Entry<Path, Path> entry : paths.entrySet()) {
            Assert.assertTrue(Files.exists(entry.getKey()));
            Assert.assertFalse(Files.exists(entry.getValue()));
            try {
                renameFileHandler.renamePath(entry.getKey(), entry.getValue());
            } catch (IOException e) {
                Assert.fail("Rename " + entry.getKey() + " to " + entry.getValue() + " fail");
            }
            Assert.assertFalse(Files.exists(entry.getKey()));
            Assert.assertTrue(Files.exists(entry.getValue()));
        }
    }
}