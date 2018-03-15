/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

    private final JMEPlayEditorSettings jmePlayEditorSettings;
    private final JMEPlayEditor jmePlayEditor;

    /**
     * Initialze all splits of editor
     *
     * @param jmePlayEditorSettings to configure splits positions
     * @param jmePlayEditor         to setup splits
     */
    @Autowired
    public JMEPlayEditorSplits(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Setup all splits in the view
     */
    @PostConstruct
    private void init() {
        il = (in) -> {
            initSplitVertical();
            initSplitHorizontal();
            initLeftChangeListener();
            jmePlayEditor.center().setCenter(splitHorizontal);
            if (jmePlayEditor.leftPlayComponent() != null) {
                clLeft = (ob, o, n) -> {
                    if (n) {
                        handleLeftComponentChange(jmePlayEditor.leftPlayComponent());
                        jmePlayEditor.stage().showingProperty().removeListener(clLeft);
                    }
                };
                jmePlayEditor.stage().showingProperty().addListener(clLeft);
            }
            initBottomChangeListener();
            if (jmePlayEditor.bottomPlayComponent() != null) {
                clBottom = (ob, o, n) -> {
                    if (n) {
                        handleBottomComponentChange(jmePlayEditor.bottomPlayComponent());
                        jmePlayEditor.stage().showingProperty().removeListener(clBottom);
                    }
                };
                jmePlayEditor.stage().showingProperty().addListener(clBottom);
            }
            jmePlayEditor.centerChange().removeListener(il);
        };
        jmePlayEditor.centerChange().addListener(il);
    }

    /**
     * Setup vertical split
     */
    private void initSplitVertical() {
        splitVertical = new SplitPane();
        splitVertical.setOrientation(Orientation.HORIZONTAL);
        TabPane tabPane = new TabPane();
        splitVertical.getItems().add(new StackPane(tabPane));
        jmePlayEditor.setTabPane(tabPane);
    }

    /**
     * Setup horizontal split
     */
    private void initSplitHorizontal() {
        splitHorizontal = new SplitPane();
        splitHorizontal.setOrientation(Orientation.VERTICAL);
        splitHorizontal.getItems().add(splitVertical);
    }

    /**
     * Change listener for left region
     * {@link JMEPlayEditor#setLeftPlayComponent(JMEPlayComponent)}
     */
    private void initLeftChangeListener() {
        jmePlayEditor.leftPlayComponentChange().addListener((ov, o, n) -> handleLeftComponentChange(n));
    }

    /**
     * Set's component to the left region
     *
     * @param comp actual left component
     */
    private void handleLeftComponentChange(JMEPlayComponent comp) {
        if (comp == null && splitVertical.getItems().size() == 2) {
            splitVertical.getItems().remove(0);
            jmePlayEditorSettings.setValue(JMEPlayEditorResources.LEFT_SELECTED_COMPONENT, "");
        } else {
            if (comp == null || comp.component() == null) {
                return;
            }
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

    /**
     * Change listener for bottom region
     * {@link JMEPlayEditor#setBottomPlayComponent(JMEPlayComponent)}
     */
    private void initBottomChangeListener() {
        jmePlayEditor.bottomPlayComponentChange().addListener((ov, o, n) -> handleBottomComponentChange(n));
    }

    /**
     * Set's component to the bottom region
     *
     * @param comp actual bottom component
     */
    private void handleBottomComponentChange(JMEPlayComponent comp) {
        if (comp == null && splitHorizontal.getItems().size() == 2) {
            splitHorizontal.getItems().remove(1);
            jmePlayEditorSettings.setValue(JMEPlayEditorResources.BOTTOM_SELECTED_COMPONENT, "");
        } else {
            if (comp == null || comp.component() == null) {
                return;
            }
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
