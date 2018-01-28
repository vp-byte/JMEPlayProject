/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui.menu;

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

    public JMEPlayEditorTopMenuFile(JMEPlayGlobalSettings jmePlayGlobalSettings, JMEPlayEditor jmePlayEditor, JMEPlayEditorLocalization jmePlayEditorLocalization) {
        this.jmePlayGlobalSettings = jmePlayGlobalSettings;
        this.jmePlayEditor = jmePlayEditor;
        this.jmePlayEditorLocalization = jmePlayEditorLocalization;
    }

    @PostConstruct
    private void init() {
        menuFile = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_FILE));

        MenuItem menuFileOpen = new MenuItem(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_OPEN_ASSETS));
        menuFile.getItems().add(menuFileOpen);
        menuFileOpen.setOnAction((event) -> openDirectoryChooser());
    }

    private void openDirectoryChooser() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_EDITOR_MENU_OPEN_ASSETS_DIALOG_TITLE));
        File defaultDirectory = new File(jmePlayGlobalSettings.rootFolder());
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(jmePlayEditor.stage());
        if (selectedDirectory != null) {
            String rootfolder = selectedDirectory.getAbsolutePath();
            jmePlayGlobalSettings.setValue(JMEPlayGlobalResources.ROOTFOLDER, rootfolder);
            jmePlayEditor.setAssetFolder(rootfolder);
        }
    }

    public Menu menu() {
        return menuFile;
    }

}
