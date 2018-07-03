/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.create.folder;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.TestFxApplicationTest;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test dialog implementation to create folder
 *
 * @author vp-byte (Vladimir Petrenko)
 * @see CreateFolderHandlerDialog
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                CreateFolderHandlerDialog.class
        })
public class CreateFolderHandlerDialogTest extends TestFxApplicationTest {

    @Autowired
    private CreateFolderHandlerDialog createFolderHandlerDialog;

    private Path path = Paths.get(System.getProperty("user.home"));

    @Override
    public void start(Stage stage) {
        super.start(stage);
        Button openDialogButton = new Button("Open dialog");
        openDialogButton.setId("buttonOpenDialog");
        openDialogButton.setOnAction((event) -> createFolderHandlerDialog.construct(path).showAndWait());
        Scene scene = new Scene(new StackPane(openDialogButton), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testClickCancel() {
        clickOn("#buttonOpenDialog");
        Dialog<Path> dialog = createFolderHandlerDialog.getDialog();
        Assert.assertNotNull(dialog);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        Assert.assertNotNull(cancelButton);
        clickOn(cancelButton);
        Assert.assertNull(dialog.getResult());
    }

    @Test
    public void testClickOk() {
        clickOn("#buttonOpenDialog");
        Dialog<Path> dialog = createFolderHandlerDialog.getDialog();
        Assert.assertNotNull(dialog);
        TextField folderNameTF = (TextField) dialog.getDialogPane().lookup(".text-field");
        Assert.assertNotNull(folderNameTF);
        String folderName = "myNewFolder";
        write(folderName);
        Assert.assertEquals(folderName, folderNameTF.getText());
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Assert.assertNotNull(okButton);
        clickOn(okButton);
        Assert.assertEquals(Paths.get("" + path, folderName), dialog.getResult());
    }

}