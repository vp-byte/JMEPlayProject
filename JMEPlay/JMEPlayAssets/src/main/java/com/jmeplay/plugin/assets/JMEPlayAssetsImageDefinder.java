/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.utils.ExtensionResolver;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Defines image from filename
 *
 * @author vp-byte(Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsImageDefinder {

    private int iconSize;

    private final JMEPlayAssetsSettings jmePlayAssetsSettings;

    @Autowired
    public JMEPlayAssetsImageDefinder(JMEPlayAssetsSettings jmePlayAssetsSettings) {
        this.jmePlayAssetsSettings = jmePlayAssetsSettings;
    }

    /**
     * Initialize JMEPlayAssetsImageDefinder
     */
    @PostConstruct
    private void init() {
        iconSize = jmePlayAssetsSettings.iconSize();
    }

    public ImageView imageByFilename(Path path) {
        if (Files.isDirectory(path)) {
            return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_FOLDER, iconSize, iconSize);
        } else {
            String fileExtension = ExtensionResolver.resolve(path);
            if (fileExtension != null) {
                switch (fileExtension.toLowerCase()) {
                    case JMEPlayAssetsResources.J3O:
                        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OBJECT, iconSize, iconSize);
                    case JMEPlayAssetsResources.J3M:
                        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_MATERIAL, iconSize, iconSize);
                    case JMEPlayAssetsResources.PNG:
                    case JMEPlayAssetsResources.JPG:
                    case JMEPlayAssetsResources.JPEG:
                        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_IMAGE, iconSize, iconSize);
                    case JMEPlayAssetsResources.FNT:
                        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_FONT, iconSize, iconSize);
                    case JMEPlayAssetsResources.OGG:
                    case JMEPlayAssetsResources.WAV:
                        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_SOUND, iconSize, iconSize);
                }
            }
        }
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_FILE, iconSize, iconSize);
    }

}
