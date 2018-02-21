/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler.dialogs;

import com.jmeplay.core.utils.PathResolver;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import javafx.application.Platform;
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
 * Implementation for rename dialog of JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsReNameDialog {

    private Type type;
    private DialogPane dialogPane;

    private Label label;
    private TextField textField;
    private GridPane grid;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public JMEPlayAssetsReNameDialog(JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
    }

    public Dialog<Path> construct(final Path path) {
        return construct(path, Type.RENAME);
    }

    public Dialog<Path> construct(final Path path, final Type type) {
        this.type = type;

        Dialog<Path> dialog = new Dialog<>();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME_TITLE));

        dialogPane = dialog.getDialogPane();
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.lookupButton(ButtonType.OK).setDisable(true);
        dialogPane.setMinWidth(300);
        dialog.setResultConverter(buttonType -> {
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData == ButtonBar.ButtonData.OK_DONE) {
                if (type == Type.RENAME) {
                    return constructPath(path, textField.getText());
                } else if (type == Type.NAME) {
                    return Paths.get("" + path, textField.getText());
                }
            }
            return null;
        });

        // -- label
        label = createLabel();

        // -- textfield
        textField = createTextField(path);
        textField.textProperty().addListener((ob, o, n) -> {
            Path pathToCreate = constructPath(path, n);
            if (Files.exists(pathToCreate) || n.isEmpty() || PathResolver.nameinvalid(n)) {
                dialogPane.lookupButton(ButtonType.OK).setDisable(true);
            } else {
                dialogPane.lookupButton(ButtonType.OK).setDisable(false);
            }
        });

        // -- grid
        grid = createGrid();
        updateGrid();

        return dialog;
    }

    private Label createLabel() {
        final Label label = new Label(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_RENAME_TEXT));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().add("content");
        label.setWrapText(true);
        label.setPrefWidth(360);
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        return label;
    }

    private TextField createTextField(final Path path) {
        final String name = PathResolver.name(path);
        final TextField textField = new TextField();
        if (type == Type.RENAME) {
            textField.setText(name);
        }
        textField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        GridPane.setFillWidth(textField, true);
        return textField;
    }

    private GridPane createGrid() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
        return grid;
    }

    private void updateGrid() {
        grid.getChildren().clear();
        grid.add(label, 0, 0);
        grid.add(textField, 1, 0);
        dialogPane.setContent(grid);
        Platform.runLater(() -> textField.requestFocus());
    }

    private Path constructPath(final Path path, final String newName) {
        final Path parentPath = path.getParent();
        final String extension = PathResolver.extension(path);
        final String point = Files.isRegularFile(path) ? "." : "";
        return Paths.get(parentPath + "/" + newName + point + extension);
    }

    public enum Type {
        RENAME,
        NAME
    }

}
