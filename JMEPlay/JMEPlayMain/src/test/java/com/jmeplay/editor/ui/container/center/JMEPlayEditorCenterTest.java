/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.center;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorSettings;
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
 * Test start of application and add center BorderPane
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayEditorSettings.class,
                JMEPlayEditor.class,
                JMEPlayEditorRoot.class,
                JMEPlayEditorScene.class,
                JMEPlayEditorContainer.class,
                JMEPlayEditorCenter.class
        })
public class JMEPlayEditorCenterTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static BorderPane center;

    @Test
    public void testJMEPlayEditorCenter() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.centerChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.center());
            center = jmePlayEditor.center();
            Assert.assertEquals(jmePlayEditor.container().getCenter(), jmePlayEditor.center());
            Assert.assertEquals("center", jmePlayEditor.center().getId());
        });
        Platform.runLater(() -> {
            Assert.assertNull(center);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(center);
        });
    }

}