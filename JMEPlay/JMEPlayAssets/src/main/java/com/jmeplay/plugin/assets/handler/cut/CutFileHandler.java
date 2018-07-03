/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.cut;

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
 * Handler to cut file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 3)
public class CutFileHandler implements JMEPlayFileHandler<TreeView<Path>> {

    private static final Logger logger = LoggerFactory.getLogger(CutFileHandler.class.getName());

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    @Autowired
    public CutFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
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
     * Menu item to support cut action
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
     * Localized label for cut action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_CUT);
    }

    /**
     * Image view for cut action
     *
     * @return image view cut
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_CUT, size, size);
    }

    /**
     * Handle cut action
     *
     * @param source of action
     */
    public void handle(final TreeView<Path> source) {
        List<Path> paths = source.getSelectionModel().getSelectedItems().stream().map(TreeItem::getValue).collect(Collectors.toList());
        cutPathsToClipboard(paths);
        ((JMEPlayAssetsTreeView) source).unmarkCutedFilesInTreeView();
        ((JMEPlayAssetsTreeView) source).markCutedFilesInTreeView();
    }

    /**
     * Cut all paths to clipboard
     *
     * @param paths to copy
     */
    void cutPathsToClipboard(final List<Path> paths) {
        paths.forEach(path -> logger.trace("File " + path + " cutted to clipboard"));
        ClipboardContent content = new ClipboardContent();
        content.putFiles(paths.stream().map(Path::toFile).collect(Collectors.toList()));
        content.put(JMEPlayClipboardFormat.JMEPLAY_FILES, JMEPlayClipboardFormat.CUT);
        if (OSInfo.OS() == OSType.LINUX) {
            content.put(JMEPlayClipboardFormat.GNOME_FILES, FileHandlerUtil.toByteBufferCut(paths));
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
    }

}
