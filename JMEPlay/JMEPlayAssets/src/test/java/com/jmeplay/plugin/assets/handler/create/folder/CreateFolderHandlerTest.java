/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.create.folder;

import com.jmeplay.core.JMEPlayGlobalSettings;
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
 * Test to create folder from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                CreateFolderHandler.class,
                CreateFolderHandlerDialog.class
        })
public class CreateFolderHandlerTest {

    @Autowired
    private CreateFolderHandler createFolderHandler;
    private static List<Path> paths;

    /**
     * Create list of folders
     */
    @Before
    public void createFoldersList() {
        paths = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            paths.add(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
        }
    }

    /**
     * Delete test folders
     */
    @After
    public void deleteCreatedFolders() {
        try {
            for (Path path : paths) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(createFolderHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(createFolderHandler.label());
        Assert.assertFalse(createFolderHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(createFolderHandler.image());
    }

    /**
     * Test to create folder on filesystem
     */
    @Test
    public void createPath() {
        paths.forEach(path -> {
            Assert.assertFalse(Files.exists(path));
            try {
                createFolderHandler.createPath(path);
            } catch (IOException e) {
                Assert.fail("Create " + path + " fail: " + e.getMessage());
            }
            Assert.assertTrue(Files.exists(path));
        });
    }
}