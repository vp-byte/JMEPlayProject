/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container;

import com.jmeplay.editor.TestFxApplicationTest;
import com.jmeplay.editor.ui.JMEPlayEditor;
import com.jmeplay.editor.ui.JMEPlayEditorRoot;
import com.jmeplay.editor.ui.JMEPlayEditorScene;
import com.jmeplay.editor.ui.container.JMEPlayEditorContainer;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test start of application and add container
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayEditor.class,
                JMEPlayEditorRoot.class,
                JMEPlayEditorScene.class,
                JMEPlayEditorContainer.class
        })
public class JMEPlayEditorContainerTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static BorderPane container;

    @Test
    public void testJMEPlayEditorContainer() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.sceneChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.container());
            container = jmePlayEditor.container();
            Assert.assertEquals("container", jmePlayEditor.container().getId());
        });
        Platform.runLater(() -> {
            Assert.assertNull(container);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(container);
        });
    }

}