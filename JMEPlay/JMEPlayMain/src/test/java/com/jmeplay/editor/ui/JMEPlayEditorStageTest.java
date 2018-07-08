/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.TestFxApplicationTest;
import javafx.application.Platform;
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
                JMEPlayGlobalSettings.class,
                JMEPlayEditorSettings.class,
                JMEPlayEditorStage.class
        })
public class JMEPlayEditorStageTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static Stage stage;

    @Test
    public void testJmePlayEditor() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.stageChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.stage());
            Assert.assertTrue(jmePlayEditor.stage().isShowing());
            stage = jmePlayEditor.stage();
        });
        Platform.runLater(() -> {
            Assert.assertNull(stage);
            jmePlayEditor.setStage(getStage());
            Assert.assertNotNull(stage);
        });
    }

}