/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.copy;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.os.OSInfo;
import com.jmeplay.core.utils.os.OSType;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.JMEPlayAssetsTreeView;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 4)
public class CopyFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(CopyFileHandler.class.getName());

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public CopyFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                           JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * Support any file type
     *
     * @return list of supported files
     */
    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    /**
     * Menu item to support copy action
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
     * Localized label for copy action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_COPY);
    }

    /**
     * Image view for copy action
     *
     * @return image view copy
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_COPY, size, size);
    }

    /**
     * Handle copy action
     *
     * @param source of action
     */
    public void handle(TreeView<Path> source) {
        List<Path> paths = source.getSelectionModel().getSelectedItems().stream().map(TreeItem::getValue).collect(Collectors.toList());

        ClipboardContent content = new ClipboardContent();
        content.putFiles(paths.stream().map(Path::toFile).collect(Collectors.toList()));
        content.put(JMEPlayClipboardFormat.JMEPLAY_FILES, JMEPlayClipboardFormat.COPY);
        if (OSInfo.OS() == OSType.LINUX) {
            content.put(JMEPlayClipboardFormat.GNOME_FILES, FileHandlerUtil.toByteBufferCopy(paths));
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
        ((JMEPlayAssetsTreeView) source).unmarkCutedFilesInTreeView();
        paths.forEach(path -> logger.trace("Copy " + path + " to clipboard"));
    }

}
