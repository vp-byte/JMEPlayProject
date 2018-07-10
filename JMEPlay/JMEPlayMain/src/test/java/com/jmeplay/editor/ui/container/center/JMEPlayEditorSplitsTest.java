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
import com.jmeplay.editor.ui.container.bottom.JMEPlayEditorInfoBar;
import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test start of application and add two SplitPanes
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
                JMEPlayEditorInfoBar.class,
                JMEPlayEditorCenter.class,
                JMEPlayEditorSplits.class
        })
public class JMEPlayEditorSplitsTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static SplitPane splitHorizontal;
    private static SplitPane splitVertical;

    @Test
    public void testJMEPlayEditorSplits() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.centerChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.center().getCenter());
            Assert.assertTrue(jmePlayEditor.center().getCenter() instanceof SplitPane);
            splitHorizontal = (SplitPane) jmePlayEditor.center().getCenter();
            splitVertical = (SplitPane) splitHorizontal.getItems().get(0);
            Assert.assertEquals("splithorizontal", jmePlayEditor.center().getCenter().getId());
        });
        Platform.runLater(() -> {
            Assert.assertNull(splitHorizontal);
            Assert.assertNull(splitVertical);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(splitHorizontal);
            Assert.assertNotNull(splitVertical);
        });
    }

}