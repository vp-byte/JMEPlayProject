/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.cut;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.os.OSInfo;
import com.jmeplay.core.utils.os.OSType;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Test to cut files from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                CutFileHandler.class
        })
public class CutFileHandlerTest extends ApplicationTest {

    @Autowired
    private CutFileHandler cutFileHandler;
    private static List<Path> paths;

    /**
     * Create text files
     */
    @BeforeClass
    public static void createFiles() {
        paths = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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
     * Delete test files
     */
    @AfterClass
    public static void deleteFiles() {
        try {
            for (Path path : paths) {
                Files.deleteIfExists(path);
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
        Assert.assertEquals(1, cutFileHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.any, cutFileHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(cutFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(cutFileHandler.label());
        Assert.assertFalse(cutFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(cutFileHandler.image());
    }

    /**
     * Test cut files to clipboard
     */
    @Test
    public void cutPathsToClipboard() {
        Platform.runLater(() -> {
            cutFileHandler.cutPathsToClipboard(paths);
            Clipboard clipboard = Clipboard.getSystemClipboard();
            if (OSInfo.OS() == OSType.LINUX) {
                final String clipboardContent = FileHandlerUtil.fromByteBuffer((ByteBuffer) clipboard.getContent(JMEPlayClipboardFormat.GNOME_FILES()));
                Assert.assertTrue(clipboardContent.contains(JMEPlayClipboardFormat.CUT));
                paths.forEach((path -> Assert.assertTrue(clipboardContent.contains(path.getFileName().toString()))));
            } else {
                final String clipboardContent = (String) clipboard.getContent(JMEPlayClipboardFormat.JMEPLAY_FILES());
                Assert.assertTrue(clipboardContent.contains(JMEPlayClipboardFormat.CUT));
                final Object clipboardFiles = clipboard.getContent(DataFormat.FILES);
                if (clipboardFiles instanceof List<?>) {
                    final ArrayList<?> filesArray = new ArrayList<>((List<?>) (clipboard.getContent(DataFormat.FILES)));
                    final ArrayList<File> files = new ArrayList<>();
                    filesArray.forEach(fileObject -> {
                        if (fileObject instanceof File) {
                            files.add((File) fileObject);
                        }
                    });
                    paths.forEach(path -> Assert.assertTrue(files.stream().anyMatch(file -> file.toPath().equals(path))));
                }
            }
        });
    }
}