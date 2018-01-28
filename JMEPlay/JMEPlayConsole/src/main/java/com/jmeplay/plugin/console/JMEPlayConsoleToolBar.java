/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Implementation of tool bar for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleToolBar extends VBox {

    private int toolsIconSize;
    private ToggleButton toggleButtonExceptions;
    private Button buttonSelectAll;
    private Button buttonCopy;
    private Button buttonClear;

    private final JMEPlayConsoleSettings jmePlayConsoleSettings;
    private final JMEPlayConsoleLocalization jmePlayConsoleLocalization;
    private final JMEPlayConsoleArea jmePlayConsoleArea;

    @Autowired
    public JMEPlayConsoleToolBar(JMEPlayConsoleSettings jmePlayConsoleSettings,
                                 JMEPlayConsoleLocalization jmePlayConsoleLocalization,
                                 JMEPlayConsoleArea jmePlayConsoleArea) {
        this.jmePlayConsoleSettings = jmePlayConsoleSettings;
        this.jmePlayConsoleLocalization = jmePlayConsoleLocalization;
        this.jmePlayConsoleArea = jmePlayConsoleArea;
    }

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        initSettings();
        setSpacing(jmePlayConsoleSettings.iconSpacing());
        initButtonClose();
        initToggleButtonLogExceptions();
        initButtonSelectAll();
        initButtonCopy();
        initButtonClear();

        jmePlayConsoleArea.setOnMouseReleased(event -> updateButtons());
        jmePlayConsoleArea.addEventHandler(JMEPlayConsoleEvent.UPDATE_TOOLBAR_BUTTONS, (in) -> updateButtons());
        updateButtons();
    }

    /**
     * Initialize settings for console tool bar
     */
    private void initSettings() {
        toolsIconSize = jmePlayConsoleSettings.iconSize();
    }

    /**
     * Initialize close button
     */
    private void initButtonClose() {
        ImageView closeImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_CLOSE, toolsIconSize, toolsIconSize);
        Button buttonClose = new Button(null, closeImage);
        String closeTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_CLOSE_TOOLTIP);
        buttonClose.setTooltip(new Tooltip(closeTooltipLabel));
        buttonClose.setOnAction(event -> fireEvent(new JMEPlayConsoleEvent(JMEPlayConsoleEvent.CLOSE_CONSOLE)));
        getChildren().add(buttonClose);
    }

    /**
     * Initialize log exceptions check Box
     */
    private void initToggleButtonLogExceptions() {
        ImageView exceptionImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_EXCEPTION, toolsIconSize, toolsIconSize);
        toggleButtonExceptions = new ToggleButton(null, exceptionImage);
        String exceptionTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_EXCEPTION_TOOLTIP);
        toggleButtonExceptions.setTooltip(new Tooltip(exceptionTooltipLabel));
        toggleButtonExceptions.setSelected(jmePlayConsoleSettings.writeExceptions());
        toggleButtonExceptions.setOnAction(event -> jmePlayConsoleSettings.setValue(JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS, toggleButtonExceptions.isSelected()));
        getChildren().add(toggleButtonExceptions);
    }

    /**
     * Initialize select all button
     */
    private void initButtonSelectAll() {
        ImageView selectAllImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_SELECTALL, toolsIconSize, toolsIconSize);
        buttonSelectAll = new Button(null, selectAllImage);
        String selectAllTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_SELECTALL_TOOLTIP);
        buttonSelectAll.setTooltip(new Tooltip(selectAllTooltipLabel));
        buttonSelectAll.setDisable(true);
        buttonSelectAll.setOnAction(event -> {
            jmePlayConsoleArea.selectAll();
            updateButtons();
        });
        getChildren().add(buttonSelectAll);
    }

    /**
     * Initialize copy button
     */
    private void initButtonCopy() {
        ImageView copyImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_COPY, toolsIconSize, toolsIconSize);
        buttonCopy = new Button(null, copyImage);
        String copyTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_COPY_TOOLTIP);
        buttonCopy.setTooltip(new Tooltip(copyTooltipLabel));
        buttonCopy.setDisable(true);
        buttonCopy.setOnAction(event -> {
            jmePlayConsoleArea.copy();
            updateButtons();
        });
        getChildren().add(buttonCopy);
    }

    /**
     * Initialize clear button
     */
    private void initButtonClear() {
        ImageView clearAllImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_DELETE, toolsIconSize, toolsIconSize);
        buttonClear = new Button(null, clearAllImage);
        String clearAllTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_CLEARALL_TOOLTIP);
        buttonClear.setTooltip(new Tooltip(clearAllTooltipLabel));
        buttonClear.setDisable(true);
        buttonClear.setOnAction(event -> {
            jmePlayConsoleArea.clear();
            updateButtons();
        });
        getChildren().add(buttonClear);
    }

    /**
     * Update buttons view
     */
    private void updateButtons() {
        boolean containsText = jmePlayConsoleArea.getText() != null && !jmePlayConsoleArea.getText().isEmpty();
        buttonSelectAll.setDisable(!containsText);
        buttonClear.setDisable(!containsText);

        boolean textSelected = jmePlayConsoleArea.getSelectedText() != null && !jmePlayConsoleArea.getSelectedText().isEmpty();
        buttonCopy.setDisable(!textSelected);
    }

}
