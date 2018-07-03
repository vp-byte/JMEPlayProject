/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.create;

import com.jmeplay.core.handler.file.JMEPlayFileCreatorHandler;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * Handler to create files
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 0)
public class CreateFileHandler implements JMEPlayFileHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private List<JMEPlayFileCreatorHandler<TreeView<Path>>> fileCreatorHandlers;

    @Autowired
    public CreateFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                             JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        size = jmePlayAssetsSettings.iconSize();
    }

    /**
     * All handlers to create files
     *
     * @param fileCreatorHandlers injected file creator handlers
     */
    @Autowired
    public void setFileCreatorHandlers(List<JMEPlayFileCreatorHandler<TreeView<Path>>> fileCreatorHandlers) {
        this.fileCreatorHandlers = fileCreatorHandlers;
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
     * Menu item to support different create actions
     *
     * @param source for MenuItem
     * @return menu item
     */
    @Override
    public MenuItem menu(TreeView<Path> source) {
        Menu menu = new Menu(label(), image());
        menu.getItems().addAll(fileCreatorHandlers.stream().map((item) -> item.menu(source)).collect(Collectors.toList()));
        return menu;
    }

    /**
     * Localized label for files creation action
     *
     * @return label rename
     */
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_NEW);
    }

    /**
     * Image view for files creation action
     *
     * @return image view copy
     */
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_NEWFILE, size, size);
    }

}
