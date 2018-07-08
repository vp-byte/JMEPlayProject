/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.editor.ui;

import com.jmeplay.editor.TestFxApplicationTest;
import javafx.scene.Group;
import javafx.scene.Parent;
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
 * @see JMEPlayEditorRoot
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayEditor.class,
                JMEPlayEditorRoot.class
        })
public class JMEPlayEditorRootTest extends TestFxApplicationTest {

    @Autowired
    private JMEPlayEditor jmePlayEditor;

    private static Parent root;

    @Test
    public void testJMEPlayEditorRoot() {
        Assert.assertNotNull(jmePlayEditor);
        jmePlayEditor.stageChange().addListener(il -> {
            Assert.assertNotNull(jmePlayEditor.root());
            root = jmePlayEditor.root();
            Assert.assertTrue(jmePlayEditor.root() instanceof Group);
            Assert.assertEquals("root", jmePlayEditor.root().getId());
        });
        Assert.assertNull(root);
        jmePlayEditor.setStage(super.getStage());
        Assert.assertNotNull(root);
        Assert.assertTrue(root instanceof Group);
    }

}