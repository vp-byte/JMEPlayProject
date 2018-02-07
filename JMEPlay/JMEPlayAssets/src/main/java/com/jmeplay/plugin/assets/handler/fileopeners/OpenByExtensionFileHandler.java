/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler.fileopeners;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.handler.file.JMEPlayFileOpenerHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Handler to open files by extension
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 0)
public class OpenByExtensionFileHandler extends JMEPlayFileOpenerHandler<TreeView<Path>> {

    private final int iconSize;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public OpenByExtensionFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                                      JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                                      JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayConsole = jmePlayConsole;
        iconSize = jmePlayAssetsSettings.iconSize();
    }

    /**
     * {@link JMEPlayFileOpenerHandler}
     */
    @Override
    public List<String> extension() {
        return Arrays.asList(JMEPlayFileHandler.file, JMEPlayFileOpenerHandler.filenoextension);
    }

    @Override
    public MenuItem menu(TreeView<Path> source) {
        MenuItem menuItem = new MenuItem(label(), image());
        menuItem.setOnAction((event) -> handle(source));
        return menuItem;
    }

    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPEN);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPEN, iconSize, iconSize);
    }

    public void handle(TreeView<Path> source) {
        jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "OPEN");
    }

    /*
     * @Override public void handle(Path path, TreeView<Path> source) {
     * EditorViewerTab tabToSelect = tabExists(path); if (tabToSelect != null) {
     * editorCenter.centerView().getSelectionModel().select(tabToSelect); return; }
     *
     * openEditorViewer(path);
     *
     * jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.ERROR, "Open file " +
     * path + " not implemented"); }
     *
     * private EditorViewerTab tabExists(final Path path) { for (Tab tab :
     * editorCenter.centerView().getTabs()) { EditorViewerTab editorViewerTab =
     * ((EditorViewerTab) tab); if (editorViewerTab.path().equals(path)) { return
     * editorViewerTab; } } return null; }
     *
     * private void openEditorViewer(Path path) { String fileExtension =
     * ExtensionResolver.resolve(path); for (EditorViewer editorViewer :
     * editorViewers) { for (String filetype : editorViewer.extension()) { if
     * (fileExtension.equalsIgnoreCase(filetype)) { EditorViewerTab tab =
     * editorViewer.view(path); editorCenter.centerView().getTabs().add(tab);
     * editorCenter.centerView().getSelectionModel().select(tab); return; } } } }
     */
}
