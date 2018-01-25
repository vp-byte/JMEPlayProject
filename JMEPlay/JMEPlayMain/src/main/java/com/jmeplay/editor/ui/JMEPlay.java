/*
 * Copyright (c) 2017, 2018, VP-BYTE and/or its affiliates. All rights reserved.
 * VP-BYTE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jmeplay.editor.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Start point for a whole JMEPlayEditor application
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@SpringBootApplication
@ComponentScan({"com.jmeplay.core", "com.jmeplay.editor", "com.jmeplay.plugin"})
public class JMEPlay extends Application {

    // Spring context
    private ConfigurableApplicationContext appContext;

    private JMEPlayEditor jmePlayEditor;

    @Autowired
    public void setJmePlayEditor(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    public static void main(String[] args) {
        SvgImageLoaderFactory.install();
        Platform.setImplicitExit(true);
        Application.launch(args);
    }

    @Override
    public void init() {
        appContext = SpringApplication.run(this.getClass());
        appContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void start(Stage stage) {
        jmePlayEditor.setStage(stage);
    }

    @Override
    public void stop() {
        appContext.stop();
    }
}
