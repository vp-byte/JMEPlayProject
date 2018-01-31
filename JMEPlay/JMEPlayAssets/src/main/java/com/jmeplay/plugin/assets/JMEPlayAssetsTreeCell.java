/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ExtensionResolver;
import com.jmeplay.plugin.assets.handler.OpenFileHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Create AssetsTreeCell to manage actions on tree cells
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class JMEPlayAssetsTreeCell extends TextFieldTreeCell<Path> {

    private List<JMEPlayFileHandler<TreeView<Path>>> jmePlayFileHandlers;
    private ContextMenu contextMenu = null;

    JMEPlayAssetsTreeCell(List<JMEPlayFileHandler<TreeView<Path>>> jmePlayFileHandlers) {
        this.jmePlayFileHandlers = jmePlayFileHandlers;
        setOnMouseClicked(this::processClick);
    }

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
                filterJMEPlayFileHandler(jmePlayFileHandlers).stream()
                        .filter(openFileHandler -> openFileHandler instanceof OpenFileHandler).findFirst()
                        .ifPresent(treeViewJMEPlayFileHandler -> ((OpenFileHandler) treeViewJMEPlayFileHandler).handle(this.getTreeView()));
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

    private ContextMenu updateContextMenu() {
        if (jmePlayFileHandlers != null) {
            ContextMenu updatedContextMenu = new ContextMenu();
            updatedContextMenu.getItems().addAll(jmePlayFileHandlers.stream().map((handler) -> handler.menu(getTreeView())).collect(Collectors.toList()));
            return updatedContextMenu;
        }
        return null;
    }

    private List<JMEPlayFileHandler<TreeView<Path>>> filterJMEPlayFileHandler(List<JMEPlayFileHandler<TreeView<Path>>> jmePlayFileHandlers) {
        String fileExtension = ExtensionResolver.resolve(getItem());
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
