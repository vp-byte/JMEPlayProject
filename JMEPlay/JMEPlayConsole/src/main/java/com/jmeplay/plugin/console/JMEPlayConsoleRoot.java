/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.console;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Root node for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleRoot extends BorderPane {

    @Getter
    private final JMEPlayConsoleToolBar jmePlayConsoleToolBar;
    @Getter
    private final JMEPlayConsoleArea jmePlayConsoleArea;

    @Autowired
    public JMEPlayConsoleRoot(JMEPlayConsoleToolBar jmePlayConsoleToolBar, JMEPlayConsoleArea jmePlayConsoleArea) {
        this.jmePlayConsoleToolBar = jmePlayConsoleToolBar;
        this.jmePlayConsoleArea = jmePlayConsoleArea;
    }

    @PostConstruct
    private void init() {
        setMinHeight(0);
        getStylesheets().add(getClass().getResource(JMEPlayConsoleResources.CSS).toExternalForm());
        setLeft(jmePlayConsoleToolBar);
        setRight(new StackPane(new VirtualizedScrollPane<>(jmePlayConsoleArea)));
    }
}
