/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation for paste option dialog of JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class PasteFileHandlerOptionDialog {

    private ToggleGroup group;
    private RadioButton replaceRadioButton;
    private RadioButton reindexRadioButton;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayAssetsSettings jmePlayAssetsSettings;

    @Autowired
    public PasteFileHandlerOptionDialog(JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                                        JMEPlayAssetsSettings jmePlayAssetsSettings) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayAssetsSettings = jmePlayAssetsSettings;
    }

    public Dialog<Option> construct() {
        group = new ToggleGroup();
        replaceRadioButton = createReplaceRadioButton();
        reindexRadioButton = createReindexRadioButton();

        final String option = jmePlayAssetsSettings.value(JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION, JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION_DEFAULT);
        if (option.equals(Option.REINDEX.name())) {
            reindexRadioButton.setSelected(true);
        } else {
            replaceRadioButton.setSelected(true);
        }
        GridPane grid = createGrid();

        final Dialog<Option> dialog = new Dialog<>();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE));
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setMinWidth(300);
        dialog.setResultConverter(buttonType -> {
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData == ButtonBar.ButtonData.OK_DONE) {
                if (replaceRadioButton.isSelected()) {
                    return Option.REPLACE;
                } else {
                    return Option.REINDEX;
                }
            }
            return null;
        });
        return dialog;
    }

    private RadioButton createReplaceRadioButton() {
        final RadioButton replaceRB = new RadioButton(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_REPLACE));
        replaceRB.setToggleGroup(group);
        replaceRB.selectedProperty().addListener((ob, o, n) -> jmePlayAssetsSettings.setValue(JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION, Option.REPLACE.name()));
        return replaceRB;
    }

    private RadioButton createReindexRadioButton() {
        final RadioButton reindexRB = new RadioButton(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_REINDEX));
        reindexRB.setPadding(new Insets(10, 0, 0, 0));
        reindexRB.setToggleGroup(group);
        reindexRB.selectedProperty().addListener((ob, o, n) -> jmePlayAssetsSettings.setValue(JMEPlayAssetsResources.PASTE_OPTIONS_DIALOG_SELECTION, Option.REINDEX.name()));
        return reindexRB;
    }


    private GridPane createGrid() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.add(replaceRadioButton, 0, 0);
        grid.add(reindexRadioButton, 0, 1);
        return grid;
    }


    public enum Option {
        REPLACE,
        REINDEX
    }
}
