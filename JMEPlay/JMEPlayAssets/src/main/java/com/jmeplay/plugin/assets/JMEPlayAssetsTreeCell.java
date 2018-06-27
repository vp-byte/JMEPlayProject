/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.PathResolver;
import com.jmeplay.plugin.assets.handler.open.extension.OpenByExtensionFileHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create AssetsTreeCell to manage actions on tree cells
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class JMEPlayAssetsTreeCell extends TextFieldTreeCell<Path> {

    private final OpenByExtensionFileHandler openByExtensionFileHandler;
    private final List<JMEPlayFileHandler<TreeView<Path>>> jmePlayFileHandlers;
    private ContextMenu contextMenu = null;

    /**
     * Implementation of cell for asset tree view
     *
     * @param openByExtensionFileHandler to open various files
     * @param jmePlayFileHandlers        list of all existing file handlers
     */
    JMEPlayAssetsTreeCell(OpenByExtensionFileHandler openByExtensionFileHandler, List<JMEPlayFileHandler<TreeView<Path>>> jmePlayFileHandlers) {
        this.openByExtensionFileHandler = openByExtensionFileHandler;
        this.jmePlayFileHandlers = jmePlayFileHandlers;
        setOnMouseClicked(this::processClick);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(Path item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getFileName().toString());
        }
    }

    /**
     * Handle a mouse click.
     */
    private void processClick(final MouseEvent event) {
        if (getItem() == null) {
            return;
        }

        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();
        }

        final MouseButton button = event.getButton();

        if (button.equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                openByExtensionFileHandler.handle(this.getTreeView());
            }
        }

        if (button == MouseButton.SECONDARY) {
            contextMenu = updateContextMenu();
            if (contextMenu == null) {
                return;
            }
            if (!contextMenu.isShowing()) {
                contextMenu.show(this, Side.BOTTOM, event.getX(), -event.getY());
            }
        }
    }

    /**
     * Update context menu
     *
     * @return updated context
     */
    private ContextMenu updateContextMenu() {
        if (jmePlayFileHandlers != null) {
            ContextMenu updatedContextMenu = new ContextMenu();
            updatedContextMenu.getItems().addAll(filterJMEPlayFileHandler().stream().map((handler) -> handler.menu(getTreeView())).collect(Collectors.toList()));
            return updatedContextMenu;
        }
        return null;
    }

    /**
     * Filter file handler for specific file type (extension of file)
     *
     * @return list of file handlers
     */
    private List<JMEPlayFileHandler<TreeView<Path>>> filterJMEPlayFileHandler() {
        String fileExtension = PathResolver.extension(getItem());
        return jmePlayFileHandlers.stream().filter(fileHandler -> {
            for (String filetype : fileHandler.filetypes()) {
                if (fileExtension != null && fileExtension.equals(filetype)) {
                    return true;
                }
                if (filetype.equals(JMEPlayFileHandler.any)) {
                    return true;
                }
                if (getItem() != null && !Files.isDirectory(getItem()) && filetype.equals(JMEPlayFileHandler.file)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}
