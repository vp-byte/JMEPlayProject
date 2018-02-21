/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.utils.PathResolver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation for JMEPlayAssets Dialogs
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsDialogs {

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayAssetsSettings jmePlayAssetsSettings;

    @Autowired
    public JMEPlayAssetsDialogs(JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                                JMEPlayAssetsSettings jmePlayAssetsSettings) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayAssetsSettings = jmePlayAssetsSettings;
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

    public Dialog<String> createPasteOptionsDialog() {
        final ToggleGroup group = new ToggleGroup();
        final RadioButton replaceRadioButton = new RadioButton(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_REPLACE));
        replaceRadioButton.setToggleGroup(group);
        replaceRadioButton.selectedProperty().addListener((ob, o, n) -> {
            jmePlayAssetsSettings.setValue(JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION, "replace");
        });

        final RadioButton reindexRadioButton = new RadioButton(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_REINDEX));
        reindexRadioButton.setPadding(new Insets(10, 0, 0, 0));
        reindexRadioButton.setToggleGroup(group);
        reindexRadioButton.selectedProperty().addListener((ob, o, n) -> {
            jmePlayAssetsSettings.setValue(JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION, "reindex");
        });
        final String option = jmePlayAssetsSettings.value(JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION, JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION_DEFAULT);
        if (option.equals("reindex")) {
            reindexRadioButton.setSelected(true);
        } else {
            replaceRadioButton.setSelected(true);
        }

        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.add(replaceRadioButton, 0, 0);
        grid.add(reindexRadioButton, 0, 1);

        final Dialog dialog = new Dialog();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE));
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setMinWidth(300);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (replaceRadioButton.isSelected()) {
                    return "replace";
                } else {
                    return "reindex";
                }
            }
            return null;
        });
        return dialog;
    }
}
