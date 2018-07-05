/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

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
import javafx.scene.input.ClipboardContent;
import org.junit.*;
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
import java.util.stream.Collectors;

/**
 * Test to paste files from menu
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                PasteFileHandler.class,
                PasteFileHandlerOptionDialog.class,
                PasteFileHandlerRenameDialog.class,
                PasteFileVisitor.class
        })
public class PasteFileHandlerTest extends ApplicationTest {

    @Autowired
    private PasteFileHandler pasteFileHandler;
    private static List<Path> paths;
    private static String directory = "paste";

    /**
     * Create text files
     */
    @BeforeClass
    public static void createFiles() {
        paths = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            paths.add(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
        }
        try {
            for (Path path : paths) {
                Files.createFile(path);
            }
            Files.createDirectory(Paths.get(System.getProperty("user.home"), directory));




        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Copy files to clipboard
     */
    @Before
    public void copyFilesToClipboard(){
        Platform.runLater(() -> {
            ClipboardContent content = new ClipboardContent();
            content.putFiles(paths.stream().map(Path::toFile).collect(Collectors.toList()));
            content.put(JMEPlayClipboardFormat.JMEPLAY_FILES, JMEPlayClipboardFormat.COPY);
            if (OSInfo.OS() == OSType.LINUX) {
                content.put(JMEPlayClipboardFormat.GNOME_FILES, FileHandlerUtil.toBuffer(JMEPlayClipboardFormat.COPY, paths));
            }
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);
        });
    }

    /**
     * Delete test file
     */
    @After
    public void deleteFiles() {
        try {
            for (Path path : paths) {
                Files.deleteIfExists(path);
                Files.deleteIfExists(Paths.get(path.getParent().toString(), directory, path.getFileName().toString()));
            }
            Files.deleteIfExists(Paths.get(System.getProperty("user.home"), directory));
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Supported file type is any
     */
    @Test
    public void filetypes() {
        Assert.assertEquals(1, pasteFileHandler.filetypes().size());
        Assert.assertEquals(JMEPlayFileHandler.any, pasteFileHandler.filetypes().get(0));
    }

    /**
     * Menu creation
     */
    @Test
    public void menu() {
        Assert.assertNotNull(pasteFileHandler.menu(null));
    }

    /**
     * Creation of simple string label
     */
    @Test
    public void label() {
        Assert.assertNotNull(pasteFileHandler.label());
        Assert.assertFalse(pasteFileHandler.label().isEmpty());
    }

    /**
     * ImageView creation
     */
    @Test
    public void image() {
        Assert.assertNotNull(pasteFileHandler.image());
    }

    @Test
    public void defineClipboardActionSetupFiles() {
        Platform.runLater(() -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            Assert.assertNotNull(clipboard);
        });
    }

}