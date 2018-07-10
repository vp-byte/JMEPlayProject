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
import javafx.scene.layout.VBox;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test start of application and add left VBox
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
                JMEPlayEditorBorderBarLeft.class
        })
public class JMEPlayEditorBorderBarLeftTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static VBox borderBarLeft;

    @Test
    public void testJMEPlayEditorBorderBarLeft() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.borderBarLeftChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.borderBarLeft());
            borderBarLeft = jmePlayEditor.borderBarLeft();
            Assert.assertEquals("borderbarleft", jmePlayEditor.borderBarLeft().getId());
        });
        Platform.runLater(() -> {
            Assert.assertNull(borderBarLeft);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(borderBarLeft);
        });
    }

}