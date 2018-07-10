/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.center;

import com.jmeplay.editor.JMEPlayEditorResources;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.ui.JMEPlayComponent;
import com.jmeplay.editor.ui.JMEPlayComponent.Align;
import com.jmeplay.editor.ui.JMEPlayEditor;
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

    /**
     * Constructor to create left border bar
     *
     * @param jmePlayEditorSettings to configure left border bar
     * @param jmePlayEditor         to setup left border bar
     */
    @Autowired
    public JMEPlayEditorBorderBarLeft(JMEPlayEditorSettings jmePlayEditorSettings, JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Set all editor components for the left region
     * {@link JMEPlayEditor#setBorderBarLeft(VBox)}
     * {@link JMEPlayEditor#borderBarLeft()}
     * {@link JMEPlayEditor#borderBarLeftChange()}
     * {@link JMEPlayEditor#setLeftPlayComponent(JMEPlayComponent)}
     * {@link JMEPlayComponent}
     * {@link JMEPlayComponent.Align#LEFT}
     *
     * @param editorComponents for left region
     */
    @Autowired(required = false)
    public void setEditorComponents(List<JMEPlayComponent> editorComponents) {
        this.editorComponents = editorComponents;
    }

    /**
     * Initialize left border bar, left region components and setup behavior
     */
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

    /**
     * Initialize left region components
     */
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

    /**
     * Initialize left border bar
     */
    private void initBorderBarLeft() {
        borderBarLeft = new VBox();
        borderBarLeft.setId("borderbarleft");
        borderBarLeft.setPrefWidth(jmePlayEditorSettings.iconSizeBar());
        borderBarLeft.getStyleClass().add("borderbar-v");
        borderBarLeft.getChildren().addAll(borderItemsLeft);
    }

    /**
     * Action to handle visibility of left bar
     */
    private void handleBarVisibility() {
        if (jmePlayEditor.borderBarsVisibility()) {
            jmePlayEditor.center().setLeft(borderBarLeft);
        } else {
            jmePlayEditor.center().setLeft(null);
        }
    }

    /**
     * Init visible label for component in the left border bar
     *
     * @param component to init label
     * @return node fully initialized
     */
    private Node initBorderItem(JMEPlayComponent component) {
        Label label = component.label();
        label.setMinHeight(jmePlayEditorSettings.iconSizeBar());
        label.getStyleClass().add("borderbar-label");
        label.setRotate(-90);
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleBorderItem(event.getSource()));
        return new Group(label);
    }

    /**
     * Handle mouse click on left border bars source
     * Remove all select classes from border bar labels
     *
     * @param source of event
     */
    private void handleBorderItem(Object source) {
        borderItemsLeft.forEach((control) -> {
            ((Group) control).getChildren().get(0).getStyleClass().remove("borderbar-label-selected");
            ((Group) control).getChildren().get(0).getStyleClass().add("borderbar-label");
        });
        handleBorderItemLeft(source);
    }

    /**
     * Select component of clicked source and add select class to source
     *
     * @param source of event
     */
    private void handleBorderItemLeft(Object source) {
        if (source == selectedLeft) {
            jmePlayEditor.setLeftPlayComponent(null);
            selectedLeft = null;
        } else {
            List<JMEPlayComponent> comp = editorComponents.stream().filter(component -> component.label() == source).collect(Collectors.toList());
            if (comp.size() > 0) {
                jmePlayEditor.setLeftPlayComponent(comp.get(0));
            }
            ((Node) source).getStyleClass().remove("borderbar-label");
            ((Node) source).getStyleClass().add("borderbar-label-selected");
            selectedLeft = source;
        }
    }

}
