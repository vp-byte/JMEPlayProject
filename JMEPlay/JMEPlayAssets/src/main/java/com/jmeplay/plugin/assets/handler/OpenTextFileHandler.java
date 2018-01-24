package com.jmeplay.plugin.assets.handler;

import static java.util.Collections.singletonList;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;

import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 2)
public class OpenTextFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

	private int size = 24;

	@Autowired
	private JMEPlayConsole jmePlayConsole;

	@Override
	public List<String> filetypes() {
		return singletonList(JMEPlayFileHandler.file);
	}

	@Override
	public String label() {
		return "Open in Texteditor";
	}

	@Override
	public String tooltip() {
		return "Open file in Texteditor";
	}

	@Override
	public ImageView image() {
		return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPENASTXT, size, size);
	}

	@Override
	public void handle(Path path, TreeView<Path> source) {
		jmePlayConsole.message(JMEPlayConsole.Type.ERROR, "Open file " + path + " in text editor");
	}
}
