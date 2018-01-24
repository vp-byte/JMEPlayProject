/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

/**
 * SplitView of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorSplits {

	private InvalidationListener il = null;
	private ChangeListener<Boolean> clLeft = null;
	private ChangeListener<Boolean> clBottom = null;

	private SplitPane splitVertical;
	private ChangeListener<Number> dividerVerticalPositionChangeListener;
	private SplitPane splitHorizontal;
	private ChangeListener<Number> dividerHorizontalPositionChangeListener;

	@Autowired
	private JMEPlayEditorSettings jmePlayEditorSettings;

	@Autowired
	private JMEPlayEditor jmePlayEditor;

	@PostConstruct
	private void init() {
		il = (in) -> {
			initSplitVertical();
			initSplitHorizontal();
			initLeftChangeListener();
			jmePlayEditor.getCenter().setCenter(splitHorizontal);
			if (jmePlayEditor.getLeftPlayComponent() != null) {
				clLeft = (ob, o, n) -> {
					if (n) {
						handleLeftComponentChange(jmePlayEditor.getLeftPlayComponent());
						jmePlayEditor.getStage().showingProperty().removeListener(clLeft);
					}
				};
				jmePlayEditor.getStage().showingProperty().addListener(clLeft);
			}
			initBottomChangeListener();
			if (jmePlayEditor.getBottomPlayComponent() != null) {
				clBottom = (ob, o, n) -> {
					if (n) {
						handleBottomComponentChange(jmePlayEditor.getBottomPlayComponent());
						jmePlayEditor.getStage().showingProperty().removeListener(clBottom);
					}
				};
				jmePlayEditor.getStage().showingProperty().addListener(clBottom);
			}
			jmePlayEditor.getCenterChange().removeListener(il);
		};
		jmePlayEditor.getCenterChange().addListener(il);
	}

	private void initSplitVertical() {
		splitVertical = new SplitPane();
		splitVertical.setOrientation(Orientation.HORIZONTAL);
		TabPane tabPane = new TabPane();
		splitVertical.getItems().add(new StackPane(tabPane));
		jmePlayEditor.setTabPane(tabPane);
	}

	private void initSplitHorizontal() {
		splitHorizontal = new SplitPane();
		splitHorizontal.setOrientation(Orientation.VERTICAL);
		splitHorizontal.getItems().add(splitVertical);
	}

	private void initLeftChangeListener() {
		jmePlayEditor.getLeftPlayComponentChange().addListener((ov, o, n) -> {
			handleLeftComponentChange(n);
		});
	}

	private void handleLeftComponentChange(JMEPlayComponent comp) {
		if (comp == null && splitVertical.getItems().size() == 2) {
			splitVertical.getItems().remove(0);
			jmePlayEditorSettings.setValue(JMEPlayEditorResources.LEFT_SELECTED_COMPONENT, "");
		} else {
			if (splitVertical.getItems().size() == 2) {
				splitVertical.getItems().remove(0);
				jmePlayEditorSettings.setValue(JMEPlayEditorResources.LEFT_SELECTED_COMPONENT, "");
			}
			splitVertical.getItems().add(0, new StackPane(comp.component()));
			jmePlayEditorSettings.setValue(JMEPlayEditorResources.LEFT_SELECTED_COMPONENT, comp.name());
			SplitPane.Divider splitVerticalDivider = splitVertical.getDividers().get(0);
			splitVerticalDivider.setPosition(jmePlayEditorSettings.value(JMEPlayEditorResources.LEFT_DIVIDER_POSITION + comp.name(), JMEPlayEditorResources.LEFT_DIVIDER_POSITION_DEFAULT));
			if (dividerVerticalPositionChangeListener != null) {
				splitVerticalDivider.positionProperty().removeListener(dividerVerticalPositionChangeListener);
			}
			dividerVerticalPositionChangeListener = (observable, oldValue, newValue) -> jmePlayEditorSettings.setValue(JMEPlayEditorResources.LEFT_DIVIDER_POSITION + comp.name(),
					newValue.doubleValue());
			splitVerticalDivider.positionProperty().addListener(dividerVerticalPositionChangeListener);
		}
	}

	private void initBottomChangeListener() {
		jmePlayEditor.getBottomPlayComponentChange().addListener((ov, o, n) -> {
			handleBottomComponentChange(n);
		});
	}

	private void handleBottomComponentChange(JMEPlayComponent comp) {
		if (comp == null && splitHorizontal.getItems().size() == 2) {
			splitHorizontal.getItems().remove(1);
			jmePlayEditorSettings.setValue(JMEPlayEditorResources.BOTTOM_SELECTED_COMPONENT, "");
		} else {
			if (splitHorizontal.getItems().size() == 2) {
				splitHorizontal.getItems().remove(1);
				jmePlayEditorSettings.setValue(JMEPlayEditorResources.BOTTOM_SELECTED_COMPONENT, "");
			}
			splitHorizontal.getItems().add(1, new StackPane(comp.component()));
			jmePlayEditorSettings.setValue(JMEPlayEditorResources.BOTTOM_SELECTED_COMPONENT, comp.name());
			SplitPane.Divider splitHorizontalDivider = splitHorizontal.getDividers().get(0);
			splitHorizontalDivider.setPosition(jmePlayEditorSettings.value(JMEPlayEditorResources.BOTTOM_DIVIDER_POSITION + comp.name(), JMEPlayEditorResources.BOTTOM_DIVIDER_POSITION_DEFAULT));
			if (dividerHorizontalPositionChangeListener != null) {
				splitHorizontalDivider.positionProperty().removeListener(dividerHorizontalPositionChangeListener);
			}
			dividerHorizontalPositionChangeListener = (observable, oldValue, newValue) -> jmePlayEditorSettings.setValue(JMEPlayEditorResources.BOTTOM_DIVIDER_POSITION + comp.name(),
					newValue.doubleValue());
			splitHorizontalDivider.positionProperty().addListener(dividerHorizontalPositionChangeListener);
		}
	}

}
