/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui.container.top.menu;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.editor.JMEPlayEditorLocalization;
import com.jmeplay.editor.JMEPlayEditorSettings;
import com.jmeplay.editor.TestFxApplicationTest;
import com.jmeplay.editor.ui.JMEPlayEditor;
import com.jmeplay.editor.ui.JMEPlayEditorRoot;
import com.jmeplay.editor.ui.JMEPlayEditorScene;
import com.jmeplay.editor.ui.container.JMEPlayEditorContainer;
import com.jmeplay.editor.ui.container.top.JMEPlayEditorTop;
import javafx.application.Platform;
import javafx.scene.control.MenuBar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
                JMEPlayEditorLocalization.class,
                JMEPlayEditor.class,
                JMEPlayEditorRoot.class,
                JMEPlayEditorScene.class,
                JMEPlayEditorContainer.class,
                JMEPlayEditorTop.class,
                JMEPlayEditorTopMenuBar.class,
                JMEPlayEditorTopMenuFile.class,
                JMEPlayEditorTopMenuSettings.class,
                JMEPlayEditorTopMenuHelp.class
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class JMEPlayEditorTopMenuSettingsTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static MenuBar menuBar;

    @Test
    public void testJMEPlayEditorTopMenuSettings() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.menuBarChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.menuBar());
            menuBar = jmePlayEditor.menuBar();
            Assert.assertEquals("menubar", jmePlayEditor.menuBar().getId());
            Assert.assertTrue(jmePlayEditor.menuBar().getMenus().stream().anyMatch(menu -> menu.getId().equals("menusettings")));
        });
        Platform.runLater(() -> {
            Assert.assertNull(menuBar);
            jmePlayEditor.setStage(super.getStage());
            Assert.assertNotNull(menuBar);
        });
    }

}