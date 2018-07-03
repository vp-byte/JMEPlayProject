/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.TestFxApplicationTest;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;

/**
 * Test dialog implementation to delete file or folder
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see DeleteFileHandlerDialog
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                DeleteFileHandlerDialog.class
        })
public class DeleteFileHandlerDialogTest extends TestFxApplicationTest {

    @Autowired
    private DeleteFileHandlerDialog deleteFileHandlerDialog;

    @Override
    public void start(Stage stage) {
        super.start(stage);
        Button openDialogButton = new Button("Open dialog");
        openDialogButton.setId("buttonOpenDialog");
        openDialogButton.setOnAction((event) -> deleteFileHandlerDialog.create().showAndWait());
        Scene scene = new Scene(new StackPane(openDialogButton), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testClickCancel() {
        clickOn("#buttonOpenDialog");
        Alert dialog = deleteFileHandlerDialog.getDialog();
        Assert.assertNotNull(dialog);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        Assert.assertNotNull(cancelButton);
        clickOn(cancelButton);
        Assert.assertEquals(ButtonType.CANCEL, dialog.getResult());
    }

    @Test
    public void testClickOk() {
        clickOn("#buttonOpenDialog");
        Alert dialog = deleteFileHandlerDialog.getDialog();
        Assert.assertNotNull(dialog);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Assert.assertNotNull(cancelButton);
        clickOn(cancelButton);
        Assert.assertEquals(ButtonType.OK, dialog.getResult());
    }

}