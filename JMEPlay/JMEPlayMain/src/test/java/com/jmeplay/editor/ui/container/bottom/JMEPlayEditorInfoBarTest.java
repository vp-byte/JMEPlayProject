/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.bottom;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.TestFxApplicationTest;
import com.jmeplay.editor.ui.JMEPlayEditor;
import com.jmeplay.editor.ui.JMEPlayEditorRoot;
import com.jmeplay.editor.ui.JMEPlayEditorScene;
import com.jmeplay.editor.ui.container.JMEPlayEditorContainer;
import com.jmeplay.editor.ui.container.bottom.JMEPlayEditorInfoBar;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test start of application and add bottom info bar
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
                JMEPlayEditorInfoBar.class
        })
public class JMEPlayEditorInfoBarTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static HBox infoBar;

    @Test
    public void testJMEPlayEditorInfoBar() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.infoBarChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.infoBar());
            infoBar = jmePlayEditor.infoBar();
            Assert.assertEquals(jmePlayEditor.container().getBottom(), jmePlayEditor.infoBar());
            Assert.assertEquals("infobar", jmePlayEditor.infoBar().getId());
            Assert.assertTrue(jmePlayEditor.infoBar().getChildren().size() > 0);
            Assert.assertNotNull(jmePlayEditor.infoBar().lookup("#modeswitcher"));
            Assert.assertNotNull(jmePlayEditor.infoBar().lookup("#infolabel"));
        });
        Platform.runLater(() -> {
            Assert.assertNull(infoBar);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(infoBar);
        });
    }

}