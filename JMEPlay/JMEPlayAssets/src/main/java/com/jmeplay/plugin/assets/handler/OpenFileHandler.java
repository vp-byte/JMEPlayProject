package com.jmeplay.plugin.assets.handler;

import static java.util.Collections.singletonList;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.ExtensionResolver;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;

import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

/**
 * Handler to open image file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 0)
public class OpenFileHandler extends JMEPlayFileHandler<TreeView<Path>> {

	@Override
	public List<String> filetypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String label() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tooltip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageView image() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handle(Path path, TreeView<Path> source) {
		// TODO Auto-generated method stub
		
	}
	 /* 
	 * private int size = 24;
	 * 
	 * @Autowired private JMEPlayConsole jmePlayConsole;
	 * 
	 * @Autowired private List<EditorViewer> editorViewers;
	 * 
	 * @Autowired private JMEEditorCenter editorCenter;
	 * 
	 * @Override public List<String> filetypes() { return
	 * singletonList(JMEPlayFileHandler.file); }
	 * 
	 * @Override public String label() { return "Open"; }
	 * 
	 * @Override public String tooltip() { return "Open file"; }
	 * 
	 * @Override public ImageView image() { return
	 * ImageLoader.imageView(this.getClass(),
	 * JMEPlayAssetsResources.ICONS_ASSETS_OPEN, size, size); }
	 * 
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
	 * editorViewers) { for (String filetype : editorViewer.filetypes()) { if
	 * (fileExtension.equalsIgnoreCase(filetype)) { EditorViewerTab tab =
	 * editorViewer.view(path); editorCenter.centerView().getTabs().add(tab);
	 * editorCenter.centerView().getSelectionModel().select(tab); return; } } } }
	 */
}
