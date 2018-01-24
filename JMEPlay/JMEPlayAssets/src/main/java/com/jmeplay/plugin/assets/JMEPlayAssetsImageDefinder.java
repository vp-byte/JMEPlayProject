package com.jmeplay.plugin.assets;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.ExtensionResolver;
import com.jmeplay.core.utils.ImageLoader;

import javafx.scene.image.ImageView;

/**
 * Defines image from filename
 *
 * @author vp-byte(Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsImageDefinder {

	private int iconSize;

	@Autowired
	private JMEPlayAssetsSettings jmePlayAssetsSettings;

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
