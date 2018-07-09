/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.top;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.TestFxApplicationTest;
import com.jmeplay.editor.ui.JMEPlayEditor;
import com.jmeplay.editor.ui.JMEPlayEditorRoot;
import com.jmeplay.editor.ui.JMEPlayEditorScene;
import com.jmeplay.editor.ui.container.JMEPlayEditorContainer;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test start of application and add top VBox
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
                JMEPlayEditorTop.class
        })
public class JMEPlayEditorTopTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static VBox top;

    @Test
    public void testJMEPlayEditorTop() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.topChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.top());
            top = jmePlayEditor.top();
            Assert.assertEquals(jmePlayEditor.container().getTop(), jmePlayEditor.top());
            Assert.assertEquals("top", jmePlayEditor.top().getId());
        });
        Platform.runLater(() -> {
            Assert.assertNull(top);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(top);
        });
    }

}