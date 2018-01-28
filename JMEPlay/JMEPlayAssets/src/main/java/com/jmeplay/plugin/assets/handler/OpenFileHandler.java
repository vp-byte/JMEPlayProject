package com.jmeplay.plugin.assets.handler;

import static java.util.Collections.singletonList;

import java.nio.file.Path;
import java.util.List;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayConsole;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsResources;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.jmeplay.core.handler.file.JMEPlayFileHandler;

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

    private final int size;
    private final JMEPlayAssetsSettings jmePlayAssetsSettings;
    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private final JMEPlayConsole jmePlayConsole;

    @Autowired
    public OpenFileHandler(JMEPlayAssetsSettings jmePlayAssetsSettings,
                           JMEPlayAssetsLocalization jmePlayAssetsLocalization,
                           JMEPlayConsole jmePlayConsole) {
        this.jmePlayAssetsSettings = jmePlayAssetsSettings;
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
        this.jmePlayConsole = jmePlayConsole;
        size = this.jmePlayAssetsSettings.iconSizeBar();
    }

    @Override
    public List<String> filetypes() {
        return singletonList(JMEPlayFileHandler.file);
    }

    @Override
    public String label() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPEN);
    }

    @Override
    public String tooltip() {
        return jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_OPEN_TOOLTIP);
    }

    @Override
    public ImageView image() {
        return ImageLoader.imageView(this.getClass(), JMEPlayAssetsResources.ICONS_ASSETS_OPEN, size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        // TODO Auto-generated method stub
        jmePlayConsole.message(JMEPlayConsole.Type.INFO, "Open file");
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
