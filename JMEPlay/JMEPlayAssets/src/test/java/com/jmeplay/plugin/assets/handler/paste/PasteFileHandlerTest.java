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

    private static List<Path> pathsToCopy = new ArrayList<>();
    private static List<Path> pathsToCut = new ArrayList<>();
    private static String dirToCopy = "copy";
    private static String dirToCut = "cut";

    /**
     * Create text files
     */
    @BeforeClass
    public static void createFiles() throws IOException {
        for (int i = 0; i < 3; i++) {
            pathsToCopy.add(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
            pathsToCut.add(Paths.get(System.getProperty("user.home"), UUID.randomUUID().toString()));
        }
        pathsToCopy.forEach(PasteFileHandlerTest::createFile);
        pathsToCut.forEach(PasteFileHandlerTest::createFile);
        Files.createDirectory(Paths.get(System.getProperty("user.home"), dirToCopy));
        Files.createDirectory(Paths.get(System.getProperty("user.home"), dirToCut));
    }

    private static void createFile(final Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    private void putFilesToClipboard(final String clipboardFormat, final List<Path> paths) {
        ClipboardContent content = new ClipboardContent();
        content.putFiles(paths.stream().map(Path::toFile).collect(Collectors.toList()));
        content.put(JMEPlayClipboardFormat.JMEPLAY_FILES, clipboardFormat);
        if (OSInfo.OS() == OSType.LINUX) {
            content.put(JMEPlayClipboardFormat.GNOME_FILES, FileHandlerUtil.toBuffer(clipboardFormat, paths));
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
    }

    /**
     * Delete test files
     */
    @AfterClass
    public static void deleteFiles() throws IOException {
        pathsToCopy.forEach(path -> deleteFile(path, dirToCopy));
        pathsToCut.forEach(path -> deleteFile(path, dirToCut));
        Files.deleteIfExists(Paths.get(System.getProperty("user.home"), dirToCopy));
        Files.deleteIfExists(Paths.get(System.getProperty("user.home"), dirToCut));
    }

    private static void deleteFile(final Path path, final String dir) {
        try {
            Files.deleteIfExists(path);
            Files.deleteIfExists(Paths.get(path.getParent().toString(), dir, path.getFileName().toString()));
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
            putFilesToClipboard(JMEPlayClipboardFormat.COPY, pathsToCopy);
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            Assert.assertNotNull(clipboard);
            List<File> files = new ArrayList<>();
            pasteFileHandler.defineClipboardActionSetupFiles(clipboard, files);
            Assert.assertTrue(files.size() > 0);
            pathsToCopy.forEach(path -> Assert.assertTrue(files.stream().anyMatch(file -> path.toString().equals(file.toString()))));
        });
    }

    @Test
    public void moveOrCopyTestCOPY() {
        Platform.runLater(() -> {
            putFilesToClipboard(JMEPlayClipboardFormat.COPY, pathsToCopy);
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            Assert.assertNotNull(clipboard);
            List<File> files = new ArrayList<>();
            pasteFileHandler.defineClipboardActionSetupFiles(clipboard, files);
            Assert.assertTrue(files.size() > 0);
            pathsToCopy.forEach(path -> Assert.assertTrue(files.stream().anyMatch(file -> path.toString().equals(file.toString()))));
            files.forEach(file -> pasteFileHandler.moveOrCopy(JMEPlayClipboardFormat.COPY, file.toPath(), Paths.get(System.getProperty("user.home"), dirToCopy, file.getName())));
            files.forEach(file -> Assert.assertTrue(file.exists()));
            files.forEach(file -> Assert.assertTrue(Paths.get(System.getProperty("user.home"), dirToCopy, file.getName()).toFile().exists()));
        });
    }

    @Test
    public void moveOrCopyTestCUT() {
        Platform.runLater(() -> {
            putFilesToClipboard(JMEPlayClipboardFormat.CUT, pathsToCut);
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            Assert.assertNotNull(clipboard);
            List<File> files = new ArrayList<>();
            pasteFileHandler.defineClipboardActionSetupFiles(clipboard, files);
            Assert.assertTrue(files.size() > 0);
            pathsToCut.forEach(path -> Assert.assertTrue(files.stream().anyMatch(file -> path.toString().equals(file.toString()))));
            files.forEach(file -> pasteFileHandler.moveOrCopy(JMEPlayClipboardFormat.CUT, file.toPath(), Paths.get(System.getProperty("user.home"), dirToCut, file.getName())));
            files.forEach(file -> Assert.assertFalse(file.exists()));
            files.forEach(file -> Assert.assertTrue(Paths.get(System.getProperty("user.home"), dirToCut, file.getName()).toFile().exists()));
        });
    }

}