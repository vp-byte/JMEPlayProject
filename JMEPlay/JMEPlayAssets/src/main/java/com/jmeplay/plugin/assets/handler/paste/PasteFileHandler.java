/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.os.OSInfo;
import com.jmeplay.core.utils.os.OSType;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.JMEPlayAssetsTreeView;
import com.jmeplay.plugin.assets.handler.rename.RenameFileHandlerDialog;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import com.jmeplay.plugin.assets.handler.util.PasteFileVisitor;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * Handler to paste file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 5)
public class PasteFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final RenameFileHandlerDialog renameFileHandlerDialog;
    private final PasteFileVisitor pasteFileVisitor;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public PasteFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                            JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                            RenameFileHandlerDialog renameFileHandlerDialog,
                            PasteFileVisitor pasteFileVisitor,
                            JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.renameFileHandlerDialog = renameFileHandlerDialog;
        this.pasteFileVisitor = pasteFileVisitor;
        this.jmePlayConsole = jmePlayConsole;
        size = jmePlayAssetsSettings.iconSize();
    }

    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    @Override
    public MenuItem menu(TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_PASTE, size, size);
    }

    public void handle(TreeView<Path> source) {

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

    @SuppressWarnings("unchecked")
    private String defineClipboardActionSetupFiles(final Clipboard clipboard, List<File> files) {
        String clipboardAction = null;
        if (OSInfo.OS() == OSType.LINUX) {
            String clipboardContent = FileHandlerUtil.fromByteBuffer((ByteBuffer) clipboard.getContent(JMEPlayClipboardFormat.GNOME_FILES));
            if (clipboardContent == null) {
                return null;
            }
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
            clipboardAction = (String) clipboard.getContent(JMEPlayClipboardFormat.JMEPLAY_FILES);
            files.addAll((List<File>) (clipboard.getContent(DataFormat.FILES)));
        }
        return clipboardAction;
    }

    private void paste(String clipboardAction, final Path targetPath, final List<File> files) {
        if (files.size() == 1) {
            File file = files.get(0);
            if (Files.isRegularFile(file.toPath())) {
                Path newFile = targetPath.resolve(file.getName());
                if (Files.exists(newFile)) {
                    Optional<Path> result = renameFileHandlerDialog.construct(newFile).showAndWait();
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
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Paste file: " + newFile + " success");
            } catch (final IOException e) {
                e.printStackTrace();
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Paste file: " + newFile + " fail");
            }
        });
    }

    private void moveOrCopy(String clipboardAction, final Path file, final Path newFile) {
        try {
            if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                Files.move(file, newFile);
            }
            if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                Files.copy(file, newFile);
            }
            jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Paste file: " + newFile + " success");
        } catch (final IOException e) {
            e.printStackTrace();
            jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Paste file: " + newFile + " fail");
        }
    }
}