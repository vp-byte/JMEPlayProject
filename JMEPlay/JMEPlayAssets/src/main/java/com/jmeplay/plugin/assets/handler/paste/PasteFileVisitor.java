/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Optional;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.FileVisitResult.TERMINATE;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * File visitor to paste files
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class PasteFileVisitor implements FileVisitor<Path> {
    private Path source;
    private Path target;
    private String clipboardAction;
    private final CopyOption[] copyOptions = new CopyOption[]{COPY_ATTRIBUTES, REPLACE_EXISTING};
    private JMEPlayAssetsPasteOptionDialog.Option pasteOption;

    private final JMEPlayAssetsPasteOptionDialog jmePlayAssetsPasteOptionDialog;

    @Autowired
    public PasteFileVisitor(JMEPlayAssetsPasteOptionDialog jmePlayAssetsPasteOptionDialog) {
        this.jmePlayAssetsPasteOptionDialog = jmePlayAssetsPasteOptionDialog;
    }

    public void action(Path source, Path target, String clipboardAction) {
        this.source = source;
        this.target = target;
        this.clipboardAction = clipboardAction;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        Path newdir = target.resolve(source.relativize(dir));
        try {
            System.out.println(dir + " -> "+ newdir);
            if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                Files.move(dir, newdir, copyOptions);
            } else if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                Files.copy(dir, newdir, copyOptions);
            }
        } catch (FileAlreadyExistsException x) {
            // ignore
        } catch (IOException x) {
            System.err.format("Unable to create: %s: %s%n", newdir, x);
            return SKIP_SUBTREE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        try {
            if (pasteOption == null && Files.exists(target)) {
                Optional<JMEPlayAssetsPasteOptionDialog.Option> result = jmePlayAssetsPasteOptionDialog.construct().showAndWait();
                result.ifPresent((option) -> pasteOption = option);
                if (!result.isPresent()) {
                    return TERMINATE;
                }
            }

            Files.copy(source, target, copyOptions);
            switch (pasteOption) {
                case REPLACE:
                    if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                        Files.move(source, target, copyOptions);
                    } else if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                        System.out.println(source + " -> "+ target);
                    }
                    break;
                case REINDEX:
                    // TODO copy / cut with REINDEX
                    break;
                default:
                    if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                        Files.move(source, target);
                    } else if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                        Files.copy(source, target);
                    }
            }

        } catch (IOException x) {
            System.err.format("Unable to copy: %s: %s%n", source, x);
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        if (exc == null) {
            Path newdir = target.resolve(source.relativize(dir));
            try {
                FileTime time = Files.getLastModifiedTime(dir);
                Files.setLastModifiedTime(newdir, time);
            } catch (IOException x) {
                System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
            }
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        if (exc instanceof FileSystemLoopException) {
            System.err.println("cycle detected: " + file);
        } else {
            System.err.format("Unable to copy: %s: %s%n", file, exc);
        }
        return CONTINUE;
    }
}
