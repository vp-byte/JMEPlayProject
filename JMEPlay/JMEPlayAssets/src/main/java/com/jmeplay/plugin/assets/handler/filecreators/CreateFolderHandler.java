package com.jmeplay.plugin.assets.handler.filecreators;

import com.jmeplay.core.handler.file.JMEPlayFileCreatorHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
@Order(value = 0)
public class CreateFolderHandler implements JMEPlayFileCreatorHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public CreateFolderHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                               JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                               JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayConsole = jmePlayConsole;
        size = jmePlayAssetsSettings.iconSize();
    }

    @Override
    public MenuItem menu(final TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_FOLDER, size, size);
    }

    public void handle(final TreeView<Path> source) {
        final Path path = pathFromSelectedItem(source);
        Optional<String> result = createInputFolderNameDialog(path).showAndWait();
        result.ifPresent((v) -> {
            final Path pathToCreate = Paths.get(path.toString(), result.get());
            try {
                Files.createDirectory(pathToCreate);
                source.getSelectionModel().getSelectedItem().setExpanded(true);
                ListChangeListener<? super TreeItem<Path>> li = change -> {
                    change.next();
                    source.getSelectionModel().clearAndSelect(source.getRow(change.getAddedSubList().get(0)));
                };
                source.getSelectionModel().getSelectedItem().getChildren().addListener(li);
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Create folder " + pathToCreate + " success");
            } catch (Exception e) {
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Create folder " + pathToCreate + " fail");
                jmePlayConsole.exception(e);
            }
        });
    }

    private Path pathFromSelectedItem(final TreeView<Path> source) {
        Path path = source.getSelectionModel().getSelectedItem().getValue();
        if (Files.isRegularFile(path)) {
            path = path.getParent();
        }
        return path;
    }

    private TextInputDialog createInputFolderNameDialog(final Path path) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TITLE));
        dialog.setHeaderText(null);
        dialog.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TEXT));
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.getEditor().textProperty().addListener((ob, o, n) -> {
            Path pathToCreate = Paths.get(path.toString(), n);
            if (Files.exists(pathToCreate) || n.isEmpty()) {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            } else {
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            }
        });
        return dialog;
    }
}
