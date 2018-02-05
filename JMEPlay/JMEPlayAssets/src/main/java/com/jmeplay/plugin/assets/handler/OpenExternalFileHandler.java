/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Handler to open file in external editor support for mac, win, linux operating systems
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 2)
public class OpenExternalFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

    private final int iconSize;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public OpenExternalFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                                   JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                                   JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayConsole = jmePlayConsole;
        iconSize = jmePlayAssetsSettings.iconSize();
    }

    /**
     * {@link JMEPlayFileHandler:filetype}
     */
    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.file);
    }

    @Override
    public MenuItem menu(TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPENEXTERNAL);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPENEXTERNAL, iconSize, iconSize);
    }

    public void handle(TreeView<Path> source) {
        Platform.runLater(new ProcessRunner(source.getSelectionModel().getSelectedItem().getValue()));
    }

    private class ProcessRunner implements Runnable {

        Path path;

        ProcessRunner(Path path) {
            this.path = path;
        }

        @Override
        public void run() {
            try {
                FileHandlerUtil.openExtern(path);
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Open file " + path + " external success");
            } catch (IllegalArgumentException e) {
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, e.getMessage());
                jmePlayConsole.exception(e);
            }
        }
    }
}
