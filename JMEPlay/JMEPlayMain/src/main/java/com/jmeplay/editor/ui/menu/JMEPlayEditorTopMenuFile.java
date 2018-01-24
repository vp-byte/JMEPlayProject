/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 */
package com.jmeplay.editor.ui.menu;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.JMEPlayGlobalResources;
import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorLocalization;
import com.jmeplay.editor.ui.JMEPlayEditor;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;

/**
 * File menu of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorTopMenuFile {

	private Menu menuFile;

	@Autowired
	private JMEPlayGlobalSettings jmePlayGlobalSettings;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@Autowired
	private JMEPlayEditorLocalization jmePlayEditorLocalization;

	@PostConstruct
	private void init() {
		menuFile = new Menu(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_MENU_FILE));

		MenuItem menuFileOpen = new MenuItem(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_MENU_OPEN_ASSETS));
		menuFile.getItems().add(menuFileOpen);
		menuFileOpen.setOnAction((event) -> {
			openDirectoryChooser();
		});
	}

	private void openDirectoryChooser() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(jmePlayEditorLocalization.value(JMEPlayEditorLocalization.LOCALIZATION_MENU_OPEN_ASSETS_DIALOG_TITLE));
		File defaultDirectory = new File(jmePlayGlobalSettings.rootFolder());
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(jmePlayEditor.getStage());
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
