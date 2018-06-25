/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.editor.ui.JMEPlayTreeView;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.dialogs.JMEPlayAssetsRenameDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
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
public class RenameFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayAssetsRenameDialog jmePlayAssetsRenameDialog;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public RenameFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                             JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                             JMEPlayAssetsRenameDialog jmePlayAssetsRenameDialog,
                             JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayAssetsRenameDialog = jmePlayAssetsRenameDialog;
        this.jmePlayConsole = jmePlayConsole;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Support any filetype
     * @return list of supported files
     */
    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    /**
     * Menu item to support rename action
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
     * Localized label for rename action
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME);
    }

    /**
     * Image view for rename action
     * @return image view rename
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_RENAME, size, size);
    }

    /**
     * Handle rename action
     * @param source of action
     */
    public void handle(TreeView<Path> source) {
        final Path path = source.getSelectionModel().getSelectedItem().getValue();
        Optional<Path> result = jmePlayAssetsRenameDialog.construct(path).showAndWait();
        result.ifPresent((newPath) -> {
            try {
                ((JMEPlayTreeView) source).setSelectAddedItem();
                Files.move(path, newPath);
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Renamed file from " + source.getSelectionModel().getSelectedItem().getValue() + " to " + result.get() + " success");
            } catch (final IOException e) {
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Renamed file from " + source.getSelectionModel().getSelectedItem().getValue() + " to " + result.get() + " fail");
            }
        });
    }

}
