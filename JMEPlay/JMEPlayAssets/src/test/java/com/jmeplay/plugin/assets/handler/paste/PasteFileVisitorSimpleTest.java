/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.TestFxApplicationTest;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

/**
 * Test visitor to paste (copy op move) files recursively
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see PasteFileVisitor
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                PasteFileVisitor.class,
                PasteFileHandlerOptionDialog.class
        })
public class PasteFileVisitorSimpleTest extends TestFxApplicationTest {

    @Autowired
    private PasteFileVisitor pasteFileVisitor;

    private static Path copyFromPath;
    private static Path copyToPath;
    private static List<Path> paths = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        /*
        super.start(stage);
        Scene scene = new Scene(new StackPane(new Button("TEST")), 800, 600);
        stage.setScene(scene);
        stage.show();
        */
    }

    /**
     * Create text files
     */
    @BeforeClass
    public static void createFiles() throws IOException {
        copyFromPath = Paths.get(System.getProperty("user.home"), "level1");
        copyToPath = Paths.get(System.getProperty("user.home"), "copy/");
        paths.add(Paths.get(copyFromPath.toString(), UUID.randomUUID().toString()));
        paths.add(Paths.get(System.getProperty("user.home"), "level1/level2", UUID.randomUUID().toString()));
        paths.add(Paths.get(System.getProperty("user.home"), "level1/level2/level3", UUID.randomUUID().toString()));
        paths.forEach(PasteFileVisitorSimpleTest::createFile);
        Files.createDirectory(copyToPath);
    }

    private static void createFile(final Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void run() throws IOException {
        for (int i = 0; i < paths.size(); i++) {
            Path newFile = copyToPath.resolve(copyFromPath.relativize(paths.get(i)));
            pasteFileVisitor.action(paths.get(i), newFile, JMEPlayClipboardFormat.COPY);
            EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
            try {
                Files.walkFileTree(paths.get(i), opts, Integer.MAX_VALUE, pasteFileVisitor);
            } catch (IOException e) {
                Assert.fail(e.getMessage());
            }
        }
    }

/**
 * Delete test files
 *
 * @AfterClass public static void deleteFiles() throws IOException {
 * pathsToCopy.forEach(copyFromPath -> deleteFile(copyFromPath, dirToCopy));
 * pathsToCut.forEach(copyFromPath -> deleteFile(copyFromPath, dirToCut));
 * Files.deleteIfExists(Paths.get(System.getProperty("user.home"), dirToCopy));
 * Files.deleteIfExists(Paths.get(System.getProperty("user.home"), dirToCut));
 * }
 * <p>
 * private static void deleteFile(final Path copyFromPath, final String dir) {
 * try {
 * Files.deleteIfExists(copyFromPath);
 * Files.deleteIfExists(Paths.get(copyFromPath.getParent().toString(), dir, copyFromPath.getFileName().toString()));
 * } catch (IOException e) {
 * Assert.fail(e.getMessage());
 * }
 * }
 */
}