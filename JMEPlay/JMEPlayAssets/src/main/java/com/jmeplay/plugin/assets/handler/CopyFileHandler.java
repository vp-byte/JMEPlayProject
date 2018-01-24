package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 3)
public class CopyFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

	private int size = 24;

	@Autowired
	private JMEPlayConsole jmePlayConsole;

	@Override
	public List<String> filetypes() {
		return singletonList(JMEPlayFileHandler.any);
	}

	@Override
	public String label() {
		return "Copy";
	}

	@Override
	public String tooltip() {
		return "Copy file to clipboard";
	}

	@Override
	public ImageView image() {
		return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_COPY, size, size);
	}

	@Override
	public void handle(Path path, TreeView<Path> source) {
		jmePlayConsole.message(JMEPlayConsole.Type.SUCCESS, "Copy " + path + " to clipboard");

		ClipboardContent content = new ClipboardContent();
		content.putFiles(singletonList(path.toFile()));

		Clipboard clipboard = Clipboard.getSystemClipboard();
		clipboard.setContent(content);
	}

}
