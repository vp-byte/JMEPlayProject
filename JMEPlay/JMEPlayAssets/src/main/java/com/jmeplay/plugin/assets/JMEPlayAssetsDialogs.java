/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.utils.PathResolver;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation for JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsDialogs {

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public JMEPlayAssetsDialogs(JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
    }

    public TextInputDialog createInputNewFileNameDialog(final Path path) {
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

    public TextInputDialog createInputFileFolderNameDialog(final Path path) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TITLE));
        dialog.setHeaderText(null);
        dialog.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TEXT));
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.getEditor().textProperty().addListener((ob, o, n) -> {
            Path pathToCreate = Paths.get(path.toString(), n);
            if (Files.exists(pathToCreate) || n.isEmpty() || PathResolver.nameinvalid(n)) {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            } else {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });
        return dialog;
    }
}
