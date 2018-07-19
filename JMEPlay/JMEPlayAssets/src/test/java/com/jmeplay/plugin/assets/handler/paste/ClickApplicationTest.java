package com.jmeplay.plugin.assets.handler.paste;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.plugin.assets.handler.util.FileHandlerUtil;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.GridPane;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;

import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Set;

public class ClickApplicationTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        Parent sceneRoot = new GridPane();
        Scene scene = new Scene(sceneRoot, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void should_contain_button() {
        Platform.runLater(() -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            Set<DataFormat> dataFormats = clipboard.getContentTypes();
/*
            dataFormats.forEach(df -> {
                System.out.println(df + " ==> " + clipboard.getContent(df));
            });*/

            System.out.println(clipboard.getContent(JMEPlayClipboardFormat.GNOME_FILES()));

            String clipboardContent = FileHandlerUtil.fromByteBuffer((ByteBuffer) clipboard.getContent(JMEPlayClipboardFormat.GNOME_FILES()));
            System.out.println(clipboardContent);
        });
    }

}