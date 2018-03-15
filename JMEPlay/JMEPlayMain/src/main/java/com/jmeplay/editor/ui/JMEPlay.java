/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Start point for a whole JMEPlay application
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@SpringBootApplication
@ComponentScan({"com.jmeplay.core", "com.jmeplay.editor", "com.jmeplay.plugin"})
public class JMEPlay extends Application {

    private ConfigurableApplicationContext appContext;
    private JMEPlayEditor jmePlayEditor;

    @Autowired
    public void setJmePlayEditor(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    /**
     * Startpoint for the whole application
     *
     * @param args of application
     */
    public static void main(String[] args) {
        SvgImageLoaderFactory.install();
        Platform.setImplicitExit(true);
        Application.launch(args);
    }

    /**
     * Init spring context
     */
    @Override
    public void init() {
        appContext = SpringApplication.run(this.getClass());
        appContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    /**
     * Load whole JMEPlay JavaFX application
     *
     * @param stage to build main view
     */
    @Override
    public void start(Stage stage) {
        jmePlayEditor.setStage(stage);
    }

    /**
     * Stops whole application
     */
    @Override
    public void stop() {
        appContext.stop();
    }
}
