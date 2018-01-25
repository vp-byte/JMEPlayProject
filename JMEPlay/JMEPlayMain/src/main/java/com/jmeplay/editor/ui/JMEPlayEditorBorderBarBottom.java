/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.ui.JMEPlayComponent.Align;
import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BorderBar for bottom items of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorBorderBarBottom {

    private InvalidationListener il = null;

    private List<Node> borderItemsBottom;
    private Object selectedLeft;
    private HBox borderBarBottom;

    private final JMEPlayEditorSettings jmePlayEditorSettings;
    private final JMEPlayEditor jmePlayEditor;
    private List<JMEPlayComponent> editorComponents;

    @Autowired
    public JMEPlayEditorBorderBarBottom(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
        this.jmePlayEditor = jmePlayEditor;
    }

    @Autowired(required = false)
    public void setEditorComponents(List<JMEPlayComponent> editorComponents) {
        this.editorComponents = editorComponents;
    }

    @PostConstruct
    public void init() {
        il = (in) -> {
            initComponentsLeft();
            initBorderBarBottom();
            jmePlayEditor.getBorderBarsVisibilityChange().addListener((ch) -> handleBarVisibility());
            handleBarVisibility();
            jmePlayEditor.setBorderBarBottom(borderBarBottom);
            jmePlayEditor.getCenterChange().removeListener(il);
        };
        jmePlayEditor.getCenterChange().addListener(il);
    }

    private void initComponentsLeft() {
        borderItemsBottom = new ArrayList<>();
        if (editorComponents == null) {
            return;
        }
        List<JMEPlayComponent> componentsBottom = editorComponents.stream().filter(component -> component.align() == Align.BOTTOM)
                .sorted(Comparator.comparing(JMEPlayComponent::priority)).collect(Collectors.toList());
        componentsBottom.forEach((component) -> {
            borderItemsBottom.add(initBorderItem(component));
            String selected = jmePlayEditorSettings.value(JMEPlayEditorResources.BOTTOM_SELECTED_COMPONENT, "");
            if (!selected.isEmpty() && component.name().equals(selected)) {
                handleBorderItem(component.label());
            }
        });
    }

    private void initBorderBarBottom() {
        borderBarBottom = new HBox();
        borderBarBottom.setPadding(new Insets(0, 0, 0, jmePlayEditorSettings.iconSizeBar()));
        borderBarBottom.setPrefHeight(jmePlayEditorSettings.iconSizeBar());
        borderBarBottom.getStyleClass().add("borderbar-h");
        borderBarBottom.getChildren().addAll(borderItemsBottom);
    }

    private void handleBarVisibility() {
        if (jmePlayEditor.getBorderBarsVisibility()) {
            jmePlayEditor.getCenter().setBottom(borderBarBottom);
        } else {
            jmePlayEditor.getCenter().setBottom(null);
        }
    }

    private Node initBorderItem(JMEPlayComponent component) {
        Label label = component.label();
        label.setMinHeight(jmePlayEditorSettings.iconSizeBar());
        label.getStyleClass().add("borderbar-label");
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleBorderItem(event.getSource()));
        return new Group(label);
    }

    private void handleBorderItem(Object source) {
        borderItemsBottom.forEach((control) -> {
            ((Group) control).getChildren().get(0).getStyleClass().remove("borderbar-label-selected");
            ((Group) control).getChildren().get(0).getStyleClass().add("borderbar-label");
        });
        handleBorderItemLeft(source);
    }

    private void handleBorderItemLeft(Object source) {
        if (source == selectedLeft) {
            jmePlayEditor.setBottomPlayComponent(null);
            selectedLeft = null;
        } else {
            List<JMEPlayComponent> comp = editorComponents.stream().filter(component -> component.label() == source).collect(Collectors.toList());
            if (comp.size() > 0) {
                jmePlayEditor.setBottomPlayComponent(comp.get(0));
            }
            ((Node) source).getStyleClass().remove("borderbar-label");
            ((Node) source).getStyleClass().add("borderbar-label-selected");
            selectedLeft = source;
        }
    }

}
