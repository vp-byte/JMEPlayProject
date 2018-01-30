/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.assets;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.editor.ui.JMEPlayGlobal;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * Create AssetsTreeView to manage dir structure and all action in JMEPlayEditor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsTreeView extends TreeView<Path> {

    private WatchService watcher;
    private Map<WatchKey, TreeItem<Path>> keys;

    private JMEPlayGlobal jmePlayGlobal;
    private JMEPlayAssetsSettings jmePlayAssetsSettings;
    private JMEPlayAssetsImageDefinder jmePlayAssetsImageDefinder;
    private List<JMEPlayFileHandler<TreeView<Path>>> fileHandlers;

    @Autowired
    public JMEPlayAssetsTreeView(JMEPlayGlobal jmePlayGlobal,
                                 JMEPlayAssetsSettings jmePlayAssetsSettings,
                                 JMEPlayAssetsImageDefinder jmePlayAssetsImageDefinder) {
        this.jmePlayGlobal = jmePlayGlobal;
        this.jmePlayAssetsSettings = jmePlayAssetsSettings;
        this.jmePlayAssetsImageDefinder = jmePlayAssetsImageDefinder;
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @Autowired(required = false)
    public void setFileHandlers(List<JMEPlayFileHandler<TreeView<Path>>> fileHandlers) {
        this.fileHandlers = fileHandlers;
    }

    /**
     * Load settings
     */
    @PostConstruct
    private void init() throws Exception {
        watcher = FileSystems.getDefault().newWatchService();
        jmePlayGlobal.assetFolderChange().addListener((in) -> reloadAssetFolder());
        reloadAssetFolder();
    }

    private void reloadAssetFolder() {
        String rootFolder = jmePlayAssetsSettings.rootFolder();
        Path rootPath = Paths.get(rootFolder);
        keys = new HashMap<>();
        TreeItem<Path> rootTreeItem = new TreeItem<>(rootPath);
        setRoot(rootTreeItem);
        setShowRoot(false);
        setCellFactory(param -> new JMEPlayAssetsTreeCell(fileHandlers));
        try {
            createTree(rootTreeItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(this::processEvents);
        thread.setDaemon(true);
        thread.start();

        jmePlayGlobal.assetFolderChange().get();
    }

    private void createTree(TreeItem<Path> treeItem) throws IOException {
        WatchKey key = treeItem.getValue().register(watcher, ENTRY_CREATE, ENTRY_DELETE);
        keys.put(key, treeItem);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(treeItem.getValue())) {
            for (Path path : directoryStream) {
                TreeItem<Path> newItem = new TreeItem<>(path, jmePlayAssetsImageDefinder.imageByFilename(path));
                treeItem.getChildren().add(newItem);
                if (Files.isDirectory(path)) {
                    createTree(newItem);
                }
            }
            // sort tree structure by name
            treeItem.getChildren().sort(Comparator.comparing(t -> t.getValue().getFileName().toString().toLowerCase()));
        }
    }

    /**
     * Process all events for keys queued to the watcher
     */
    private void processEvents() {
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key).getValue();
            if (dir == null) {
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                Path name = ((WatchEvent<Path>) event).context();
                Path child = dir.resolve(name);

                if (kind == ENTRY_CREATE) {
                    try {
                        if (keys.get(key).getChildren().stream().noneMatch((ch) -> ch.getValue().equals(child))) {
                            TreeItem<Path> item = new TreeItem<>(child, jmePlayAssetsImageDefinder.imageByFilename(child));
                            if (Files.isDirectory(child)) {
                                createTree(item);
                            }

                            keys.get(key).getChildren().add(item);
                        }
                    } catch (IOException x) {
                        x.printStackTrace();
                    }
                }
                if (kind == ENTRY_DELETE) {
                    keys.get(key).getChildren().stream().filter((ch) -> ch.getValue().equals(child)).findFirst().ifPresent(
                            (first) -> keys.get(key).getChildren().remove(first));
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

}
