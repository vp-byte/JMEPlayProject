/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation for paste option dialog of JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class PasteFileHandlerOptionDialog {

    @Getter
    private Dialog<PasteFileOptionSelection> dialog;

    private CheckBox checkBoxApplyToAll;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public PasteFileHandlerOptionDialog(JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
    }

    Dialog<PasteFileOptionSelection> construct() {
        checkBoxApplyToAll = createCheckBox();

        GridPane grid = createGrid();

        dialog = new Dialog<>();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE));
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.YES)).setText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_REINDEX));
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.NO)).setText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_REPLACE));
        dialog.getDialogPane().setMinWidth(400);
        dialog.setResultConverter(buttonType -> {
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData == ButtonBar.ButtonData.YES) {
                if (checkBoxApplyToAll.isSelected()) {
                    return PasteFileOptionSelection.REPLACE_ALL;
                } else {
                    return PasteFileOptionSelection.REPLACE;
                }
            } else if (buttonData == ButtonBar.ButtonData.NO) {
                if (checkBoxApplyToAll.isSelected()) {
                    return PasteFileOptionSelection.REINDEX_ALL;
                } else {
                    return PasteFileOptionSelection.REINDEX;
                }
            }
            return PasteFileOptionSelection.CANCEL;
        });
        return dialog;
    }

    private CheckBox createCheckBox() {
        return new CheckBox(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_PASTE_APPLY_TO_ALL));
    }

    private GridPane createGrid() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.add(checkBoxApplyToAll, 0, 0);
        return grid;
    }

}
