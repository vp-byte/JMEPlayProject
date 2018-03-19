/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayComponent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Implementation for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleComponent extends JMEPlayComponent {

    private Label label;

    private final JMEPlayConsoleSettings jmePlayConsoleSettings;
    private final JMEPlayConsoleLocalization jmePlayConsoleLocalization;
    private final JMEPlayConsoleRoot jmePlayConsoleRoot;

    /**
     * Constructor to create console component
     *
     * @param jmePlayConsoleSettings     to configure console
     * @param jmePlayConsoleLocalization to localize console
     * @param jmePlayConsoleRoot         to get the root node of console
     */
    @Autowired
    public JMEPlayConsoleComponent(JMEPlayConsoleSettings jmePlayConsoleSettings,
                                   JMEPlayConsoleLocalization jmePlayConsoleLocalization,
                                   JMEPlayConsoleRoot jmePlayConsoleRoot) {
        this.jmePlayConsoleSettings = jmePlayConsoleSettings;
        this.jmePlayConsoleLocalization = jmePlayConsoleLocalization;
        this.jmePlayConsoleRoot = jmePlayConsoleRoot;
    }

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        this.setPriority(0);
        setAlignment(Align.BOTTOM);
        createLabel();

        // Event handler
        this.jmePlayConsoleRoot.getJmePlayConsoleToolBar().addEventHandler(JMEPlayConsoleEvent.CLOSE_CONSOLE, (event) -> {
            MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
            label().fireEvent(mouseEvent);
        });
    }

    /**
     * Create label for JMEPlayConsole
     */
    private void createLabel() {
        String labelText = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_CONSOLE);
        int iconSizeBar = jmePlayConsoleSettings.iconSizeBar();
        label = new Label(labelText, ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_CONSOLE, iconSizeBar, iconSizeBar));
    }

    /**
     * {@link JMEPlayComponent:name}
     *
     * @return name
     */
    @Override
    public String name() {
        return jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_CONSOLE);
    }

    /**
     * {@link JMEPlayComponent:description}
     *
     * @return description
     */
    @Override
    public String description() {
        return jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.LOCALIZATION_CONSOLE_DESCRIPTION);
    }

    /**
     * {@link JMEPlayComponent:label}
     *
     * @return label
     */
    @Override
    public Label label() {
        return label;
    }

    /**
     * {@link JMEPlayComponent:component}
     *
     * @return component as node
     */
    @Override
    public Node component() {
        return jmePlayConsoleRoot;
    }

}
