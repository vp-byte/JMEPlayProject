package com.jmeplay.plugin.assets.handler;

import static java.util.Collections.singletonList;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;

import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

/**
 * Handler to paste file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 5)
public class RenameFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

	private int size = 24;

	@Override
	public List<String> filetypes() {
		return singletonList(JMEPlayFileHandler.any);
	}

	@Override
	public String label() {
		return "Rename";
	}

	@Override
	public String tooltip() {
		return "Rename file / folder";
	}

	@Override
	public ImageView image() {
		return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_RENAME, size, size);
	}

	@Override
	public void handle(Path path, TreeView<Path> source) {
		System.out.println(path);
	}
}
