/*
 * MIT-LICENSE copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor;

import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Base class for all TestFX based tests
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public abstract class TestFxApplicationTest extends ApplicationTest {

    private Stage stage;

    protected Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        System.setProperty("java.awt.headless", "false");
    }

}