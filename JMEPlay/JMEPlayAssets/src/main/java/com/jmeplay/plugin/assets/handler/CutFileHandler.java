/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.OSInfo;
import com.jmeplay.editor.ui.JMEPlayConsole;
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
public class CutFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public CutFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                          JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                          JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayConsole = jmePlayConsole;
        size = jmePlayAssetsSettings.iconSize();
    }

    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.any);
    }

    @Override
    public MenuItem menu(TreeView<Path> treeView) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(treeView));
        return menuItem;
    }

    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_CUT);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_CUT, size, size);
    }

    public void handle(TreeView<Path> treeView) {
        List<Path> paths = treeView.getSelectionModel().getSelectedItems().stream().map(TreeItem::getValue).collect(Collectors.toList());

        ClipboardContent content = new ClipboardContent();
        content.putFiles(paths.stream().map(Path::toFile).collect(Collectors.toList()));
        content.put(JMEPlayClipboardFormat.JMEPLAY_FILES, "cut");
        if (OSInfo.OS() == OSInfo.OSType.UNIX || OSInfo.OS() == OSInfo.OSType.POSIX_UNIX) {
            content.put(JMEPlayClipboardFormat.GNOME_FILES, FileHandlerUtil.toByteBufferCut(paths));
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);


        ((JMEPlayAssetsTreeView) treeView).unmarkCutedFilesInTreeView();
        ((JMEPlayAssetsTreeView) treeView).markCutedFilesInTreeView();
        paths.forEach(path -> jmePlayConsole.message(JMEPlayConsole.Type.INFO, "Cut " + path + " to clipboard"));
    }


}
