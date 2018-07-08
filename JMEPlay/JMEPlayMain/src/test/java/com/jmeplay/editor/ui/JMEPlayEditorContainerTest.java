/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.TestFxApplicationTest;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test start of application and add root
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayEditor.class,
                JMEPlayEditorRoot.class
        })
public class JMEPlayEditorContainerTest extends TestFxApplicationTest {

    private JMEPlayEditor jmePlayEditor;

    @Autowired
    public void setJmePlayEditor(JMEPlayEditor jmePlayEditor) {
        this.jmePlayEditor = jmePlayEditor;
    }

    @Override
    public void start(Stage stage) {
        super.start(stage);
        jmePlayEditor.setStage(stage);
        SvgImageLoaderFactory.install();
        Scene scene = new Scene(new StackPane(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testShow() {
        Assert.assertNotNull(jmePlayEditor);
        Assert.assertNotNull(jmePlayEditor.stage());
        Assert.assertNotNull(jmePlayEditor.root());
        Assert.assertNotNull(jmePlayEditor.scene());
        Assert.assertNotNull(jmePlayEditor.container());
        Assert.assertEquals("container", jmePlayEditor.container().getId());
    }

}