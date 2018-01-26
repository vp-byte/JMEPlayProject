package com.jmeplay.plugin.assets;

import com.jmeplay.core.handler.file.JMEPlayFileHandler;
import com.jmeplay.editor.ui.JMEPlayGlobal;
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

@Component
public class JMEPlayAssetsTreeView extends TreeView<Path> {

    private WatchService watcher;
    private Map<WatchKey, TreeItem<Path>> keys;
    private TreeItem<Path> rootTreeItem;

    @Autowired
    private JMEPlayGlobal jmePlayGlobal;

    @Autowired
    private JMEPlayAssetsSettings jmePlayAssetsSettings;

    @Autowired
    private JMEPlayAssetsImageDefinder jmePlayAssetsImageDefinder;

    @Autowired
    private List<JMEPlayFileHandler<TreeView<Path>>> fileHandlers;

    /**
     * Load settings
     */
    @PostConstruct
    private void init() throws Exception {
        watcher = FileSystems.getDefault().newWatchService();
        jmePlayGlobal.assetFolderChange().addListener((in) -> {
            reloadAssetFolder();
        });
        reloadAssetFolder();
    }

    private void reloadAssetFolder() {
        String rootFolder = jmePlayAssetsSettings.rootFolder();
        Path rootPath = Paths.get(rootFolder);
        keys = new HashMap<>();
        rootTreeItem = new TreeItem<>(rootPath);
        setRoot(rootTreeItem);
        setShowRoot(false);
        setCellFactory(param -> new JMEPlayAssetsTreeCell(fileHandlers));
        try {
            createTree(rootTreeItem);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Thread thread = new Thread(() -> processEvents());
        thread.setDaemon(true);
        thread.start();

        jmePlayGlobal.assetFolderChange().get();
    }

    private void createTree(TreeItem<Path> treeItem) throws IOException {
        WatchKey key = treeItem.getValue().register(watcher, ENTRY_CREATE, ENTRY_DELETE);
        keys.put(key, treeItem);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(treeItem.getValue())) {
            for (Path path : directoryStream) {
                TreeItem<Path> newItem = new TreeItem(path, jmePlayAssetsImageDefinder.imageByFilename(path));
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
    void processEvents() {
        while (true) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key).getValue();
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                @SuppressWarnings("rawtypes")
                WatchEvent.Kind kind = event.kind();

                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                Path name = ((WatchEvent<Path>) event).context();
                Path child = dir.resolve(name);

                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);

                // if directory is created, and watching recursively, then register it and its
                // sub-directories
                if (kind == ENTRY_CREATE) {
                    try {
                        TreeItem<Path> item = new TreeItem(child, jmePlayAssetsImageDefinder.imageByFilename(child));
                        if (Files.isDirectory(child)) {
                            createTree(item);
                        }

                        keys.get(key).getChildren().add(item);
                    } catch (IOException x) {
                        // do something useful
                    }
                }
                if (kind == ENTRY_DELETE) {
                    TreeItem<Path> item = keys.get(key).getChildren().stream().filter((ch) -> ch.getValue().equals(child)).findFirst().get();
                    keys.get(key).getChildren().remove(item);
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

}
