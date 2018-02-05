package com.jmeplay.plugin.assets.handler.util;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.utils.OSInfo;
import com.jmeplay.plugin.assets.JMEPlayAssetsTreeView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClipboardWatcher implements Runnable {

    private boolean unmark = false;

    private Clipboard clipboard = Clipboard.getSystemClipboard();
    private JMEPlayAssetsTreeView treeView;

    public ClipboardWatcher(JMEPlayAssetsTreeView treeView) {
        this.treeView = treeView;
    }

    @Override
    public void run() {
        while (!unmark) {
            Platform.runLater(this::rass);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void rass() {
        String content = null;
        if (OSInfo.OS() == OSInfo.OSType.UNIX || OSInfo.OS() == OSInfo.OSType.POSIX_UNIX) {
            content = FileHandlerUtil.fromByteBuffer((ByteBuffer) clipboard.getContent(JMEPlayClipboardFormat.GNOME_FILES));
            if (content == null || !content.startsWith("cut\n")) {
                System.out.println(content);
                unmark = true;
            }
        } else {
            content = (String) clipboard.getContent(JMEPlayClipboardFormat.JMEPLAY_FILES);
            if (content == null || !content.equals("cut")) {
                unmark = true;
            }
        }
        if (unmark) {
            treeView.unmarkCutedFilesInTreeView();
        }
    }

}
