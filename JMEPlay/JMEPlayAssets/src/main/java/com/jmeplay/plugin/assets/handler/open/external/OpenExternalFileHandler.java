/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.open.external;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.handler.file.JMEPlayFileOpenerHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Handler to open file in external editor support for mac, win, linux operating systems
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 1)
public class OpenExternalFileHandler implements JMEPlayFileOpenerHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(OpenExternalFileHandler.class.getName());

    private final int iconSize;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public OpenExternalFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                                   JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        iconSize = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Support any type of file, no support for folders
     *
     * @return list of supported files
     */
    @Override
    public List<String> extension() {
        return Arrays.asList(JMEPlayFileHandler.file, JMEPlayFileOpenerHandler.filenoextension);
    }

    /**
     * Menu item to support open file external
     *
     * @param source for MenuItem
     * @return menu item
     */
    @Override
    public MenuItem menu(TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    /**
     * Localized label for open file external action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPENEXTERNAL);
    }

    /**
     * Image view for open file external action
     *
     * @return image view copy
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPENEXTERNAL, iconSize, iconSize);
    }

    /**
     * Handle open file external action
     *
     * @param source of action
     */
    public void handle(TreeView<Path> source) {
        Platform.runLater(new ProcessRunner(source.getSelectionModel().getSelectedItem().getValue()));
    }

    /**
     * Runnable to open file external in another thread
     */
    private class ProcessRunner implements Runnable {

        Path path;

        ProcessRunner(Path path) {
            this.path = path;
        }

        @Override
        public void run() {
            try {
                FileHandlerUtil.openExtern(path);
                logger.trace("Open file " + path + " external success");
            } catch (IllegalArgumentException e) {
                logger.trace("Open file " + path + " external fail", e);
            }
        }
    }
}
