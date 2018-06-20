/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
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
 * Implementation for name dialog of JMEPlayAssets
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsNameDialog {

    private DialogPane dialogPane;

    private Label label;
    private TextField textField;
    private GridPane grid;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    /**
     * Constructor to create name or rename dialog
     *
     * @param jmePlayAssetsLocalization localisation for dialog
     */
    @Autowired
    public JMEPlayAssetsNameDialog(JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
    }

    /**
     * Construct dialog
     *
     * @param path for file
     * @return dialog
     */
    public Dialog<Path> construct(final Path path) {
        Dialog<Path> dialog = new Dialog<>();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NAME_TITLE));

        dialogPane = dialog.getDialogPane();
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.lookupButton(ButtonType.OK).setDisable(true);
        dialogPane.setMinWidth(300);
        dialog.setResultConverter(buttonType -> {
            ButtonBar.ButtonData buttonData = buttonType.getButtonData();
            if (buttonData == ButtonBar.ButtonData.OK_DONE) {
                return constructPath(path);
            }
            return null;
        });

        // -- label
        label = createLabel();

        // -- textfield
        textField = createTextField();
        textField.textProperty().addListener((ob, o, n) -> {
            Path pathToCreate = constructPath(path);
            if (Files.exists(pathToCreate) || n.isEmpty() || PathResolver.isNameInvalid(n)) {
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

    /**
     * Create label of dialog
     *
     * @return label
     */
    private Label createLabel() {
        final Label label = new Label(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NAME_TEXT));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().add("content");
        label.setWrapText(true);
        label.setPrefWidth(360);
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        return label;
    }

    /**
     * Create text field of dialog
     *
     * @return text field
     */
    private TextField createTextField() {
        final TextField textField = new TextField();
        textField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        GridPane.setFillWidth(textField, true);
        return textField;
    }

    /**
     * Create grid of dialog
     *
     * @return grid
     */
    private GridPane createGrid() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER_LEFT);
        return grid;
    }

    /**
     * Update grid
     */
    private void updateGrid() {
        grid.getChildren().clear();
        grid.add(label, 0, 0);
        grid.add(textField, 1, 0);
        dialogPane.setContent(grid);
        Platform.runLater(() -> textField.requestFocus());
    }

    /**
     * Create path for nane dialog
     *
     * @param path of file
     * @return fully qualified path
     */
    private Path constructPath(final Path path) {
        return Paths.get("" + path, textField.getText());
    }

}
