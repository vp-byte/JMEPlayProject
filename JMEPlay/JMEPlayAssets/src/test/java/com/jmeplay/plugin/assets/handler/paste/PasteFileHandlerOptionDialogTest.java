package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.core.JMEPlayGlobalSettings;
import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import com.jmeplay.plugin.assets.JMEPlayAssetsSettings;
import com.jmeplay.plugin.assets.TestFxApplicationTest;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

// Run Options: -Djava.awt.headless=false
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                JMEPlayGlobalSettings.class,
                JMEPlayAssetsSettings.class,
                JMEPlayAssetsLocalization.class,
                PasteFileHandlerOptionDialog.class
        })
public class PasteFileHandlerOptionDialogTest extends TestFxApplicationTest {

    @Autowired
    private PasteFileHandlerOptionDialog pasteFileHandlerOptionDialog;

    private Optional<PasteFileOptionSelection> result;

    @Override
    public void start(Stage stage) {
        super.start(stage);
        Button button = new Button("Open dialog");
        button.setId("buttonOpenDialog");
        button.setOnAction((event) -> {
            result = pasteFileHandlerOptionDialog.construct().showAndWait();
        });
        Scene scene = new Scene(new StackPane(button), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testCancelClick() {
        clickOn("#buttonOpenDialog");
        Dialog<PasteFileOptionSelection> dialog = pasteFileHandlerOptionDialog.getDialog();
        Assert.assertNotNull(dialog);
        Button buttonCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        Assert.assertNotNull(buttonCancel);
        clickOn(buttonCancel);
        Assert.assertEquals(PasteFileOptionSelection.CANCEL, result.get());
    }

    @Test
    public void testReplaceClick() {
        clickOn("#buttonOpenDialog");
        Dialog<PasteFileOptionSelection> dialog = pasteFileHandlerOptionDialog.getDialog();
        Assert.assertNotNull(dialog);
        Button buttonCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.YES);
        Assert.assertNotNull(buttonCancel);
        clickOn(buttonCancel);
        Assert.assertEquals(PasteFileOptionSelection.REPLACE, result.get());
    }

    @Test
    public void testReindexClick() {
        clickOn("#buttonOpenDialog");
        sleep(500);
        Dialog<PasteFileOptionSelection> dialog = pasteFileHandlerOptionDialog.getDialog();
        Assert.assertNotNull(dialog);
        Button buttonCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.NO);
        Assert.assertNotNull(buttonCancel);
        clickOn(buttonCancel);
        Assert.assertEquals(PasteFileOptionSelection.REINDEX, result.get());
    }

    @Test
    public void testReplaceAllClick() {
        clickOn("#buttonOpenDialog");
        Dialog<PasteFileOptionSelection> dialog = pasteFileHandlerOptionDialog.getDialog();
        Assert.assertNotNull(dialog);
        CheckBox checkBox = (CheckBox) dialog.getDialogPane().lookup(".check-box");
        Assert.assertNotNull(checkBox);
        clickOn(checkBox);
        Button buttonCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.YES);
        Assert.assertNotNull(buttonCancel);
        clickOn(buttonCancel);
        Assert.assertEquals(PasteFileOptionSelection.REPLACE_ALL, result.get());
    }

    @Test
    public void testReindexAllClick() {
        clickOn("#buttonOpenDialog");
        Dialog<PasteFileOptionSelection> dialog = pasteFileHandlerOptionDialog.getDialog();
        Assert.assertNotNull(dialog);
        CheckBox checkBox = (CheckBox) dialog.getDialogPane().lookup(".check-box");
        Assert.assertNotNull(checkBox);
        clickOn(checkBox);
        Button buttonCancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.NO);
        Assert.assertNotNull(buttonCancel);
        clickOn(buttonCancel);
        Assert.assertEquals(PasteFileOptionSelection.REINDEX_ALL, result.get());
    }

}