/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private static final Logger logger = LoggerFactory.getLogger(DeleteFileHandler.class.getName());

    private final int size;
    private TreeView<Path> source = null;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final DeleteFileHandlerDialog deleteFileHandlerDialog;

    @Autowired
    public DeleteFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                             JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                             DeleteFileHandlerDialog deleteFileHandlerDialog) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.deleteFileHandlerDialog = deleteFileHandlerDialog;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Support any filetype
     *
     * @return list of supported files
     */
    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    /**
     * Menu item to support delete action
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
     * Localized label for delete action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE);
    }

    /**
     * Image view for delete action
     *
     * @return image view delete
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_DELETE, size, size);
    }

    /**
     * Handle delete action
     *
     * @param source of action
     */
    public void handle(final TreeView<Path> source) {
        this.source = source;

        Optional<ButtonType> result = deleteFileHandlerDialog.create().showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            executeDelete();
        }
    }

    /**
     * Execute delete action
     */
    private void executeDelete() {
        new ArrayList<>(source.getSelectionModel().getSelectedItems()).forEach(this::deleteItem);
        source.getSelectionModel().clearSelection();
    }

    /**
     * Delete source with path
     *
     * @param source with path to delete
     */
    private void deleteItem(final TreeItem<Path> source) {

        final Path path = source.getValue();
        try {
            deletePath(path);
            source.getParent().getChildren().remove(source);
        } catch (IOException e) {
            logger.trace("Delete file " + path + " fail", e);
        }
    }

    /**
     * Delete path from file system
     *
     * @param path to delete
     * @throws IOException if delete fail
     */
    void deletePath(final Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            Files.delete(path);
            logger.trace("File " + path + " deleted");
        } else {
            Files.walkFileTree(path, new DeleteFileVisitor());
        }
    }

}
