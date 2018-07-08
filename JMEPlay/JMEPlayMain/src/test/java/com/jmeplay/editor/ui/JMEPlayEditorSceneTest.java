/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.TestFxApplicationTest;
import javafx.application.Platform;
import javafx.scene.Scene;
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
                JMEPlayEditorRoot.class,
                JMEPlayEditorScene.class
        })
public class JMEPlayEditorSceneTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static Scene scene;

    @Test
    public void testJmePlayEditor() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.sceneChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.scene());
            scene = jmePlayEditor.scene();
        });
        Platform.runLater(() -> {
            Assert.assertNull(scene);
            jmePlayEditor.setStage(getStage());
            Assert.assertNotNull(scene);
        });
    }

}