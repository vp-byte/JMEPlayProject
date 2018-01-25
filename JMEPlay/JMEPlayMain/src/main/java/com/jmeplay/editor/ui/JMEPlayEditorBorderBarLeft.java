/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.ui.JMEPlayComponent.Align;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BorderBar for left items of JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorBorderBarLeft {

    private InvalidationListener il = null;

    private List<Node> borderItemsLeft;
    private Object selectedLeft;
    private VBox borderBarLeft;

    private final JMEPlayEditorSettings jmePlayEditorSettings;
    private final JMEPlayEditor jmePlayEditor;
    private List<JMEPlayComponent> editorComponents;

    @Autowired
    public JMEPlayEditorBorderBarLeft(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
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
            initBorderBarLeft();
            jmePlayEditor.borderBarsVisibilityChange().addListener((ch) -> handleBarVisibility());
            handleBarVisibility();
            jmePlayEditor.setBorderBarLeft(borderBarLeft);
            jmePlayEditor.centerChange().removeListener(il);
        };
        jmePlayEditor.centerChange().addListener(il);
    }

    private void initComponentsLeft() {
        borderItemsLeft = new ArrayList<>();
        if (editorComponents == null) {
            return;
        }
        List<JMEPlayComponent> componentsLeft = editorComponents.stream().filter(component -> component.align() == Align.LEFT).sorted(Comparator.comparing(JMEPlayComponent::priority)).collect(Collectors.toList());
        componentsLeft.forEach((component) -> {
            borderItemsLeft.add(initBorderItem(component));
            String selected = jmePlayEditorSettings.value(JMEPlayEditorResources.LEFT_SELECTED_COMPONENT, "");
            if (!selected.isEmpty() && component.name().equals(selected)) {
                handleBorderItem(component.label());
            }
        });
    }

    private void initBorderBarLeft() {
        borderBarLeft = new VBox();
        borderBarLeft.setPrefWidth(jmePlayEditorSettings.iconSizeBar());
        borderBarLeft.getStyleClass().add("borderbar-v");
        borderBarLeft.getChildren().addAll(borderItemsLeft);
    }

    private void handleBarVisibility() {
        if (jmePlayEditor.borderBarsVisibility()) {
            jmePlayEditor.center().setLeft(borderBarLeft);
        } else {
            jmePlayEditor.center().setLeft(null);
        }
    }

    private Node initBorderItem(JMEPlayComponent component) {
        Label label = component.label();
        label.setMinHeight(jmePlayEditorSettings.iconSizeBar());
        label.getStyleClass().add("borderbar-label");
        label.setRotate(-90);
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleBorderItem(event.getSource()));
        return new Group(label);
    }

    private void handleBorderItem(Object label) {
        borderItemsLeft.forEach((control) -> {
            ((Group) control).getChildren().get(0).getStyleClass().remove("borderbar-label-selected");
            ((Group) control).getChildren().get(0).getStyleClass().add("borderbar-label");
        });
        handleBorderItemLeft(label);
    }

    private void handleBorderItemLeft(Object label) {
        if (label == selectedLeft) {
            jmePlayEditor.setLeftPlayComponent(null);
            selectedLeft = null;
        } else {
            List<JMEPlayComponent> comp = editorComponents.stream().filter(component -> component.label() == label).collect(Collectors.toList());
            if (comp.size() > 0) {
                jmePlayEditor.setLeftPlayComponent(comp.get(0));
            }
            ((Node) label).getStyleClass().remove("borderbar-label");
            ((Node) label).getStyleClass().add("borderbar-label-selected");
            selectedLeft = label;
        }
    }

}
