/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

/**
 * Handler to delete file from file system and tree view
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 7)
public class DeleteFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

    private final int size;
    private TreeView<Path> source = null;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public DeleteFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                             JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                             JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
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
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_DELETE, size, size);
    }

    public void handle(TreeView<Path> source) {
        this.source = source;

        Optional<ButtonType> result = createConfirmAlert().showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                executeDelete();
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Delete done");
            } catch (IllegalArgumentException e) {
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Delete fail");
                jmePlayConsole.exception(e);
            }
        }
    }

    private Alert createConfirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_TITLE));
        alert.setHeaderText(null);
        alert.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_QUESTION));
        return alert;
    }

    private void executeDelete() {
        new ArrayList<>(source.getSelectionModel().getSelectedItems()).forEach((item) -> delete(item.getValue()));
    }

    private void delete(Path path) {
        try {
            if (!Files.isDirectory(path)) {
                Files.delete(path);
            } else {
                Files.walkFileTree(path, new DeleteFileVisitor());
            }
            TreeItem<Path> treeItem = source.getSelectionModel().getSelectedItem();
            treeItem.getParent().getChildren().remove(treeItem);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private class DeleteFileVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }
    }
}
