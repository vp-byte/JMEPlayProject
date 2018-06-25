/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.copy;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Test to copy files from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                CopyFileHandler.class
        })
public class CopyFileHandlerTest {

    @Autowired
    private CopyFileHandler copyFileHandler;
    private static Path path;
    private static List<Path> paths;

    /**
     * Create text file
     */
    @Before
    public void createFile() {
        path = Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString());
        paths = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            paths.add(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
        }
        try {
            Files.createFile(path);
            for (int i = 0; i < paths.size(); i++) {
                Files.createFile(paths.get(i));
            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Delete test file
     */
    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }


    /**
     * Supported file type is any
     */
    @Test
    public void filetypes() {
        Assert.assertTrue(copyFileHandler.filetypes().size() == 1);
        Assert.assertTrue(copyFileHandler.filetypes().get(0) == JMEPlayFileHandler.any);
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(copyFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(copyFileHandler.label());
        Assert.assertFalse(copyFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(copyFileHandler.image());
    }

    @Test
    public void handleCopySingleFile() {
        hier weiter
    }


    @Test
    public void handleCopyMultiplyFiles() {
    }
}