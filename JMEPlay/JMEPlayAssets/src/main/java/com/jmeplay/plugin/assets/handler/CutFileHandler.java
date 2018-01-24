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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * Handler to cut file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 4)
public class CutFileHandler extends JMEPlayFileHandler<TreeView<Path>> {
	private int size = 24;

	@Autowired
	private JMEPlayConsole jmePlayConsole;

	@Override
	public List<String> filetypes() {
		return singletonList(JMEPlayFileHandler.any);
	}

	@Override
	public String label() {
		return "Cut";
	}

	@Override
	public String tooltip() {
		return "Cut file";
	}

	@Override
	public ImageView image() {
		return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_CUT, size, size);
	}

	@Override
	public void handle(Path path, TreeView<Path> source) {
		jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Cut " + path + " to clipboard");

		ClipboardContent content = new ClipboardContent();
		content.putFiles(singletonList(path.toFile()));

		Clipboard clipboard = Clipboard.getSystemClipboard();
		clipboard.setContent(content);
	}
}
