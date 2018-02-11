/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.PathResolver;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.editor.ui.JMEPlayTreeView;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public RenameFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
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
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_RENAME, size, size);
    }

    public void handle(TreeView<Path> source) {
        final Path path = source.getSelectionModel().getSelectedItem().getValue();
        Optional<String> result = createInputNewFileNameDialog(path).showAndWait();
        result.ifPresent((v) -> {
            try {
                final Path parenPath = path.getParent();
                final String extension = PathResolver.extension(path);
                ((JMEPlayTreeView) source).setSelectAddedItem();
                String point = Files.isRegularFile(path) ? "." : "";
                Files.move(path, Paths.get(parenPath + "/" + result.get() + point + extension));
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Renamed file from " + source.getSelectionModel().getSelectedItem().getValue() + " to " + result.get() + " success");
            } catch (final IOException e) {
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Renamed file from " + source.getSelectionModel().getSelectedItem().getValue() + " to " + result.get() + " fail");
            }
        });
    }

    private TextInputDialog createInputNewFileNameDialog(final Path path) {
        final String filename = PathResolver.name(path);
        final Path parentPath = path.getParent();
        final String extension = PathResolver.extension(path);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME_TITLE));
        dialog.setHeaderText(null);
        dialog.getEditor().setText(filename);
        dialog.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME_TEXT));
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.getEditor().textProperty().addListener((ob, o, n) -> {
            Path pathToCreate = Paths.get(parentPath + "/" + n + "." + extension);
            if (Files.exists(pathToCreate) || n.isEmpty() || PathResolver.nameinvalid(n)) {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            } else {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });
        return dialog;
    }
}
