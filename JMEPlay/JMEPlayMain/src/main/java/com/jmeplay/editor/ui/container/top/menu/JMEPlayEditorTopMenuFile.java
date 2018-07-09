/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.top.menu;

import com.jmeplay.core.JMEPlayGlobalResources;
import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorLocalization;
import com.jmeplay.editor.ui.JMEPlayEditor;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;

/**
 * File menu of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuFile {

    private Menu menuFile;

    private final JMEPlayGlobalSettings jmePlayGlobalSettings;
    private final JMEPlayEditor jmePlayEditor;
    private final JMEPlayEditorLocalization jmePlayEditorLocalization;

    /**
     * Constructor to create top menu of editor
     *
     * @param jmePlayGlobalSettings     to configure top file menu
     * @param jmePlayEditor             to setup top file menu
     * @param jmePlayEditorLocalization localization of file menu
     */
    public JMEPlayEditorTopMenuFile(JMEPlayGlobalSettings jmePlayGlobalSettings,
                                    JMEPlayEditor jmePlayEditor,
                                    JMEPlayEditorLocalization jmePlayEditorLocalization) {
        this.jmePlayGlobalSettings = jmePlayGlobalSettings;
        this.jmePlayEditor = jmePlayEditor;
        this.jmePlayEditorLocalization = jmePlayEditorLocalization;
    }

    /**
     * Initialize file menu
     */
    @PostConstruct
    private void init() {
        menuFile = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_FILE));
        menuFile.setId("menufile");
        MenuItem menuFileOpen = new MenuItem(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_OPEN_ASSETS));
        menuFile.getItems().add(menuFileOpen);
        menuFileOpen.setOnAction((event) -> openDirectoryChooser());
    }

    /**
     * Shows directory chooser to define main directory for editor
     */
    private void openDirectoryChooser() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_OPEN_ASSETS_DIALOG_TITLE));
        File defaultDirectory = new File(jmePlayGlobalSettings.rootFolder());
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(jmePlayEditor.stage());
        if (selectedDirectory != null) {
            Path rootfolder = selectedDirectory.toPath();
            jmePlayGlobalSettings.setValue(JMEPlayGlobalResources.ROOTFOLDER, "" + rootfolder);
            jmePlayEditor.setAssetFolder(rootfolder);
        }
    }

    /**
     * Top file menu
     *
     * @return file menu
     */
    Menu menu() {
        return menuFile;
    }

}
