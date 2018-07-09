/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.top.toolbar;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.TestFxApplicationTest;
import com.jmeplay.editor.ui.JMEPlayEditor;
import com.jmeplay.editor.ui.JMEPlayEditorRoot;
import com.jmeplay.editor.ui.JMEPlayEditorScene;
import com.jmeplay.editor.ui.container.JMEPlayEditorContainer;
import com.jmeplay.editor.ui.container.top.JMEPlayEditorTop;
import javafx.application.Platform;
import javafx.scene.control.ToolBar;
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
                JMEPlayEditorTop.class,
                JMEPlayEditorTopToolBar.class
        })
public class JMEPlayEditorTopToolBarTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static ToolBar toolBar;

    @Test
    public void testJMEPlayEditorTopToolBar() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.toolBarChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.toolBar());
            toolBar = jmePlayEditor.toolBar();
            Assert.assertEquals("toolbar", jmePlayEditor.toolBar().getId());
        });
        Platform.runLater(() -> {
            Assert.assertNull(toolBar);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(toolBar);
        });
    }

}