/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.rename;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayTreeView;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.MenuItem;
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
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

/**
 * Handler to rename file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 6)
public class RenameFileHandler implements JMEPlayFileHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(RenameFileHandler.class.getName());

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final RenameFileHandlerDialog renameFileHandlerDialog;

    @Autowired
    public RenameFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                             JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                             RenameFileHandlerDialog renameFileHandlerDialog) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.renameFileHandlerDialog = renameFileHandlerDialog;
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
     * Menu item to support rename action
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
     * Localized label for rename action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME);
    }

    /**
     * Image view for rename action
     *
     * @return image view rename
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_RENAME, size, size);
    }

    /**
     * Handle rename action
     *
     * @param source of action
     */
    public void handle(final TreeView<Path> source) {
        final Path path = source.getSelectionModel().getSelectedItem().getValue();
        Optional<Path> result = renameFileHandlerDialog.construct(path).showAndWait();
        result.ifPresent((newPath) -> {
            try {
                ((JMEPlayTreeView) source).setSelectAddedItem();
                renamePath(path, newPath);
                logger.trace("File " + path + " renamed to " + newPath);
            } catch (final IOException e) {
                logger.trace("File " + path + " rename to " + newPath + " fail", e);
            }
        });
    }

    /**
     * Rename path to new path
     *
     * @param path    old name
     * @param newPath new name
     * @throws IOException if rename fail
     */
    void renamePath(final Path path, final Path newPath) throws IOException {
        Files.move(path, newPath);
    }

}
