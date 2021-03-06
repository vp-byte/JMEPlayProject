/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets.handler.open;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.handler.file.JMEPlayFileOpenerHandler;
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
 * Handler to open files
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 1)
public class OpenFileHandler implements JMEPlayFileHandler<TreeView<Path>> {

    private final int size;

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private List<JMEPlayFileOpenerHandler<TreeView<Path>>> fileOpenerHandlers;

    @Autowired
    public OpenFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                           JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        size = jmePlayAssetsSettings.iconSize();
    }

    @Autowired
    public void setFileOpenerHandlers(final List<JMEPlayFileOpenerHandler<TreeView<Path>>> fileOpenerHandlers) {
        this.fileOpenerHandlers = fileOpenerHandlers;
    }

    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.file);
    }

    @Override
    public MenuItem menu(final TreeView<Path> source) {
        Menu menu = new Menu(label(), image());
        menu.getItems().addAll(fileOpenerHandlers.stream().map((item) -> item.menu(source)).collect(Collectors.toList()));
        return menu;
    }

    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPEN);
    }

    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPEN, size, size);
    }

}
