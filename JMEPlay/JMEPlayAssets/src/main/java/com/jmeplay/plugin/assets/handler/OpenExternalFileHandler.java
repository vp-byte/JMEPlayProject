/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.SystemOpener;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.application.Platform;
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
@Order(value = 1)
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
        iconSize = jmePlayAssetsSettings.iconSizeBar();
    }

    /**
     * {@link JMEPlayFileHandler:filetype}
     */
    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.file);
    }

    /**
     * {@link JMEPlayFileHandler:name}
     */
    @Override
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPENEXTERNAL);
    }

    /**
     * {@link JMEPlayFileHandler:description}
     */
    @Override
    public String tooltip() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPENEXTERNAL_TOOLTIP);
    }

    /**
     * {@link JMEPlayFileHandler:image}
     */
    @Override
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPENEXTERNAL, iconSize, iconSize);
    }

    /**
     * Handle event to open file in external editor
     *
     * @param path   to the file
     * @param source of event
     */
    @Override
    public void handle(Path path, TreeView<Path> source) {
        Platform.runLater(new ProcessRunner(path));
    }

    private class ProcessRunner implements Runnable {

        Path path;

        ProcessRunner(Path path) {
            this.path = path;
        }

        @Override
        public void run() {
            try {
                SystemOpener.openExtern(path);
                jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Open file " + path + " external success");
            } catch (IllegalArgumentException e) {
                jmePlayConsole.message(JMEPlayConsole.Type.ERROR, e.getMessage());
                jmePlayConsole.exception(e);
            }
        }
    }
}
