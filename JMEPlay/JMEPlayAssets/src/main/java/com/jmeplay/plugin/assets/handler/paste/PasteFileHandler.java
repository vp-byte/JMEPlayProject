/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.os.OSInfo;
import com.jmeplay.core.utils.os.OSType;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.JMEPlayAssetsTreeView;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * Handler to paste file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 5)
public class PasteFileHandler implements JMEPlayFileHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(PasteFileHandler.class.getName());

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final PasteFileHandlerRenameDialog pasteFileHandlerRenameDialog;
    private final PasteFileVisitor pasteFileVisitor;

    @Autowired
    public PasteFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                            JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                            PasteFileHandlerRenameDialog pasteFileHandlerRenameDialog,
                            PasteFileVisitor pasteFileVisitor) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.pasteFileHandlerRenameDialog = pasteFileHandlerRenameDialog;
        this.pasteFileVisitor = pasteFileVisitor;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Support any file type
     *
     * @return list of supported files
     */
    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    /**
     * Menu item to support paste action
     *
     * @param source for MenuItem
     * @return menu item
     */
    @Override
    public MenuItem menu(final TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    /**
     * Localized label for paste action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE);
    }

    /**
     * Image view for paste action
     *
     * @return image view cut
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_PASTE, size, size);
    }

    public void handle(final TreeView<Path> source) {

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard == null) return;

        List<File> files = new ArrayList<>();
        String clipboardAction = defineClipboardActionSetupFiles(clipboard, files);
        if (clipboardAction == null || files.size() <= 0) {
            return;
        }

        Path targetPath = source.getSelectionModel().getSelectedItem().getValue();
        if (Files.isRegularFile(targetPath)) {
            targetPath = targetPath.getParent();
        }

        paste(clipboardAction, targetPath, files);

        ((JMEPlayAssetsTreeView) source).unmarkCutedFilesInTreeView();
    }

    String defineClipboardActionSetupFiles(final Clipboard clipboard, final List<File> files) {
        String clipboardAction = null;
        if (OSInfo.OS() == OSType.LINUX) {
            String clipboardContent = FileHandlerUtil.fromByteBuffer((ByteBuffer) clipboard.getContent(JMEPlayClipboardFormat.GNOME_FILES()));
            clipboardContent = clipboardContent.replace("file:", "");
            clipboardContent = clipboardContent.replace("%20", " ");
            StringTokenizer tokenizer = new StringTokenizer(clipboardContent, "\n");
            int counter = 0;
            while (tokenizer.hasMoreTokens()) {
                if (counter == 0) {
                    clipboardAction = tokenizer.nextToken();
                }
                files.add(Paths.get(tokenizer.nextToken()).toFile());
                counter++;
            }
        } else {
            clipboardAction = (String) clipboard.getContent(JMEPlayClipboardFormat.JMEPLAY_FILES());
            final ArrayList<?> filesArray = new ArrayList<>((List<?>) (clipboard.getContent(DataFormat.FILES)));
            filesArray.forEach(fileObject -> {
                if (fileObject instanceof File) {
                    files.add((File) fileObject);
                }
            });
        }
        return clipboardAction;
    }

    private void paste(final String clipboardAction, final Path targetPath, final List<File> files) {
        if (files.size() == 1) {
            File file = files.get(0);
            if (Files.isRegularFile(file.toPath())) {
                Path newFile = targetPath.resolve(file.getName());
                if (Files.exists(newFile)) {
                    Optional<Path> result = pasteFileHandlerRenameDialog.construct(newFile).showAndWait();
                    result.ifPresent((newPath) -> moveOrCopy(clipboardAction, file.toPath(), newPath));
                } else {
                    moveOrCopy(clipboardAction, file.toPath(), newFile);
                }
                return;
            }
        }

        files.forEach(file -> {
            Path newFile = targetPath.resolve(file.getName());
            try {
                EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                pasteFileVisitor.action(file.toPath(), newFile, clipboardAction);
                Files.walkFileTree(file.toPath(), opts, Integer.MAX_VALUE, pasteFileVisitor);
                logger.trace("Paste file: " + newFile + " success");
            } catch (final IOException e) {
                e.printStackTrace();
                logger.trace("Paste file: " + newFile + " fail");
            }
        });
    }

    void moveOrCopy(final String clipboardAction, final Path file, final Path newFile) {
        try {
            if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                Files.move(file, newFile);
            }
            if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                Files.copy(file, newFile);
            }
            logger.trace("Paste file: " + newFile + " success");
        } catch (final IOException e) {
            e.printStackTrace();
            logger.trace("Paste file: " + newFile + " fail");
        }
    }
}
