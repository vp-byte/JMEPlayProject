package com.jmeplay.plugin.assets.handler;

import static java.util.Collections.singletonList;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;

import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;

/**
 * Handler to paste file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 5)
public class PasteFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

	private int size = 24;

	@Override
	public List<String> filetypes() {
		return singletonList(JMEPlayFileHandler.any);
	}

	@Override
	public String label() {
		return "Paste";
	}

	@Override
	public String tooltip() {
		return "Paste file to clipboard";
	}

	@Override
	public ImageView image() {
		return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_PASTE, size, size);
	}

	@Override
	public void handle(Path path, TreeView<Path> source) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		if (clipboard == null) {
			return;
		}

		final List<File> files = (List<File>) clipboard.getContent(DataFormat.FILES);
		if (files == null || files.isEmpty()) {
			return;
		}

		Map<Path, Path> pasteOrder = new HashMap<>();
		files.forEach(filepath -> {
			try {
				fillPasteOrder(filepath.toPath(), pasteOrder);
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		pasteOrder.forEach((k, v) -> System.err.println(v));

		// Dateien einzeln kopieren und unter umstaenden umbenennen
	}

	private void fillPasteOrder(Path path, Map<Path, Path> pasteOrder) throws IOException {
		if (!Files.isDirectory(path)) {
			pasteOrder.put(path, path);
			return;
		}
		pasteOrder.put(path, path);
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
			for (Path subpath : directoryStream) {
				pasteOrder.put(subpath, subpath);
				if (Files.isDirectory(subpath)) {
					fillPasteOrder(subpath, pasteOrder);
				}
			}
		}
	}
}
