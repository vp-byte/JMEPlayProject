/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

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

    private final int size;
    private TreeView<Path> source = null;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final DeleteFileHandlerDialog deleteFileHandlerDialog;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public DeleteFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                             JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                             DeleteFileHandlerDialog deleteFileHandlerDialog,
                             JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.deleteFileHandlerDialog = deleteFileHandlerDialog;
        this.jmePlayConsole = jmePlayConsole;
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
    public MenuItem menu(TreeView<Path> source) {
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
    public void handle(TreeView<Path> source) {
        this.source = source;

        Optional<ButtonType> result = deleteFileHandlerDialog.create().showAndWait();
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

    /**
     * Execute delete action
     */
    private void executeDelete() {
        new ArrayList<>(source.getSelectionModel().getSelectedItems()).forEach(this::delete);
        source.getSelectionModel().clearSelection();
    }

    /**
     * Delete path from file system
     *
     * @param item to delete
     */
    private void delete(TreeItem<Path> item) {
        final Path path = item.getValue();
        try {
            if (!Files.isDirectory(path)) {
                Files.delete(path);
            } else {
                Files.walkFileTree(path, new DeleteFileVisitor());
            }
            item.getParent().getChildren().remove(item);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
