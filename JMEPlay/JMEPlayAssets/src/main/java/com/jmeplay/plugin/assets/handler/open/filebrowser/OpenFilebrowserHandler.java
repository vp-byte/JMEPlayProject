/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.open.filebrowser;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Handler to open file or folder in system file browser.
 * Support mac, win and linux as operating systems
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 8)
public class OpenFilebrowserHandler implements JMEPlayFileHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(OpenFilebrowserHandler.class.getName());

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public OpenFilebrowserHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                                  JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Support any file type
     *
     * @return list of supported files
     */
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    /**
     * Menu item to support open file in file browser
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
     * Localized label for open file in file browser action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPENSYSTEMEXPLORER);
    }

    /**
     * Image view for open file in file browser action
     *
     * @return image view copy
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPENSYSTEMEXPLORER, size, size);
    }

    /**
     * Handle copy action
     *
     * @param source of action
     */
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
                if (Files.isRegularFile(path)) {
                    path = path.getParent();
                }
                FileHandlerUtil.openInSystem(path);
                logger.trace(path + " opened in system file browser");
            } catch (IllegalArgumentException e) {
                logger.trace("Open " + path + " in system file browser fail", e);
            }
        }
    }
}
