package com.jmeplay.plugin.assets.handler.filecreators;

import com.jmeplay.core.handler.file.JMEPlayFileCreatorHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.editor.ui.JMEPlayTreeView;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.dialogs.JMEPlayAssetsReNameDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
@Order(value = 0)
public class CreateFolderHandler implements JMEPlayFileCreatorHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayAssetsReNameDialog jmePlayAssetsReNameDialog;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public CreateFolderHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                               JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                               JMEPlayAssetsReNameDialog jmePlayAssetsReNameDialog,
                               JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayAssetsReNameDialog = jmePlayAssetsReNameDialog;
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
        Dialog<Path> dialog = jmePlayAssetsReNameDialog.construct(path, JMEPlayAssetsReNameDialog.Type.NAME);
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TITLE));
        dialog.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER_TEXT));
        Optional<Path> result = dialog.showAndWait();
        result.ifPresent((pathToCreate) -> {
            try {
                source.getSelectionModel().getSelectedItem().setExpanded(true);
                ((JMEPlayTreeView) source).setSelectAddedItem();
                Files.createDirectory(pathToCreate);
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


}
