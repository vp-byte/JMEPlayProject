/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.create.folder;

import com.jmeplay.core.handler.file.JMEPlayFileCreatorHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayTreeView;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Handler to create folder
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 0)
public class CreateFolderHandler implements JMEPlayFileCreatorHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(CreateFolderHandler.class.getName());

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final CreateFolderHandlerDialog createFolderHandlerDialog;

    @Autowired
    public CreateFolderHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                               JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                               CreateFolderHandlerDialog createFolderHandlerDialog) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.createFolderHandlerDialog = createFolderHandlerDialog;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Menu item to support folder create action
     *
     * @param source for MenuItem
     * @return menu item
     */
    @Override
    public MenuItem menu(final TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    /**
     * Localized label for create folder action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW_FOLDER);
    }

    /**
     * Image view for create folder action
     *
     * @return image view delete
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_FOLDER, size, size);
    }

    /**
     * Handle create folder action
     *
     * @param source of action
     */
    public void handle(final TreeView<Path> source) {
        final Path path = pathFromSelectedItem(source);
        Optional<Path> result = createFolderHandlerDialog.construct(path).showAndWait();
        result.ifPresent((pathToCreate) -> {
            try {
                source.getSelectionModel().getSelectedItem().setExpanded(true);
                ((JMEPlayTreeView) source).setSelectAddedItem();
                createPath(pathToCreate);
                logger.trace("Directory " + pathToCreate + " created");
            } catch (Exception e) {
                logger.trace("Directory " + pathToCreate + " creation fail");
            }
        });
    }

    /**
     * Extract folder path from selected item
     *
     * @param source of event
     * @return extracted path
     */
    private Path pathFromSelectedItem(final TreeView<Path> source) {
        Path path = source.getSelectionModel().getSelectedItem().getValue();
        if (Files.isRegularFile(path)) {
            path = path.getParent();
        }
        return path;
    }

    /**
     * Create path folder on file system
     *
     * @param path to create
     * @throws IOException if create fail
     */
    void createPath(final Path path) throws IOException {
        Files.createDirectory(path);
    }

}
