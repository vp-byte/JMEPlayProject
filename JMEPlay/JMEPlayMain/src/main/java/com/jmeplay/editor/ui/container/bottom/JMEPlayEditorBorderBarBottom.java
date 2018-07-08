/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.bottom;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.ui.JMEPlayComponent;
import com.jmeplay.editor.ui.JMEPlayComponent.Align;
import com.jmeplay.editor.ui.JMEPlayEditor;
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
    private Object selectedBottom;
    private HBox borderBarBottom;

    private final JMEPlayEditorSettings jmePlayEditorSettings;
    private final JMEPlayEditor jmePlayEditor;
    private List<JMEPlayComponent> editorComponents;

    /**
     * Constructor to create bottom border bar
     *
     * @param jmePlayEditorSettings to configure bottom border bar
     * @param jmePlayEditor         to setup bottom border bar
     */
    @Autowired
    public JMEPlayEditorBorderBarBottom(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Set all editor components for the bottom region
     * {@link JMEPlayEditor#setBorderBarBottom(HBox)}
     * {@link JMEPlayEditor#setBottomPlayComponent(JMEPlayComponent)}
     * {@link JMEPlayComponent}
     * {@link JMEPlayComponent.Align#BOTTOM}
     *
     * @param editorComponents for bottom region
     */
    @Autowired(required = false)
    public void setEditorComponents(List<JMEPlayComponent> editorComponents) {
        this.editorComponents = editorComponents;
    }

    /**
     * Initialize bottom border bar, bottom region components and setup behavior
     */
    @PostConstruct
    public void init() {
        il = (in) -> {
            initComponentsBottom();
            initBorderBarBottom();
            jmePlayEditor.borderBarsVisibilityChange().addListener((ch) -> handleBarVisibility());
            handleBarVisibility();
            jmePlayEditor.setBorderBarBottom(borderBarBottom);
            jmePlayEditor.centerChange().removeListener(il);
        };
        jmePlayEditor.centerChange().addListener(il);
    }

    /**
     * Initialize bottom region components
     */
    private void initComponentsBottom() {
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

    /**
     * Initialize bottom border bar
     */
    private void initBorderBarBottom() {
        borderBarBottom = new HBox();
        borderBarBottom.setPadding(new Insets(0, 0, 0, jmePlayEditorSettings.iconSizeBar()));
        borderBarBottom.setPrefHeight(jmePlayEditorSettings.iconSizeBar());
        borderBarBottom.getStyleClass().add("borderbar-h");
        borderBarBottom.getChildren().addAll(borderItemsBottom);
    }

    /**
     * Action to handle visibility of bottom bar
     */
    private void handleBarVisibility() {
        if (jmePlayEditor.borderBarsVisibility()) {
            jmePlayEditor.center().setBottom(borderBarBottom);
        } else {
            jmePlayEditor.center().setBottom(null);
        }
    }

    /**
     * Init visible label for component in the bottom border bar
     *
     * @param component to init label
     * @return node fully initialized
     */
    private Node initBorderItem(JMEPlayComponent component) {
        Label label = component.label();
        label.setMinHeight(jmePlayEditorSettings.iconSizeBar());
        label.getStyleClass().add("borderbar-label");
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleBorderItem(event.getSource()));
        return new Group(label);
    }

    /**
     * Handle mouse click on bottom border bars label
     * Remove all select classes from border bar labels
     *
     * @param source of event
     */
    private void handleBorderItem(Object source) {
        borderItemsBottom.forEach((control) -> {
            ((Group) control).getChildren().get(0).getStyleClass().remove("borderbar-label-selected");
            ((Group) control).getChildren().get(0).getStyleClass().add("borderbar-label");
        });
        handleBorderItemBottom(source);
    }

    /**
     * Select component of clicked label and add select class to label
     *
     * @param source of event
     */
    private void handleBorderItemBottom(Object source) {
        if (source == selectedBottom) {
            jmePlayEditor.setBottomPlayComponent(null);
            selectedBottom = null;
        } else {
            List<JMEPlayComponent> comp = editorComponents.stream().filter(component -> component.label() == source).collect(Collectors.toList());
            if (comp.size() > 0) {
                jmePlayEditor.setBottomPlayComponent(comp.get(0));
            }
            ((Node) source).getStyleClass().remove("borderbar-label");
            ((Node) source).getStyleClass().add("borderbar-label-selected");
            selectedBottom = source;
        }
    }

}
