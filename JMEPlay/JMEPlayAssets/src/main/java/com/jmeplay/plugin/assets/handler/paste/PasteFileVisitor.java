/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.paste;

import com.jmeplay.core.handler.file.JMEPlayClipboardFormat;
import com.jmeplay.core.utils.PathResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Optional;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * File visitor to paste files
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class PasteFileVisitor implements FileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(PasteFileVisitor.class.getName());

    private Path source;
    private Path target;
    private String clipboardAction;
    private final CopyOption[] copyOptions = new CopyOption[]{COPY_ATTRIBUTES, REPLACE_EXISTING};
    private PasteFileOptionSelection pasteFileOptionSelection;

    private final PasteFileHandlerOptionDialog pasteFileHandlerOptionDialog;

    @Autowired
    public PasteFileVisitor(PasteFileHandlerOptionDialog pasteFileHandlerOptionDialog) {
        this.pasteFileHandlerOptionDialog = pasteFileHandlerOptionDialog;
    }

    public void action(Path source, Path target, String clipboardAction) {
        pasteFileOptionSelection = PasteFileOptionSelection.DEFAULT;
        this.source = source;
        this.target = target;
        this.clipboardAction = clipboardAction;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        Path newdir = target.resolve(source.relativize(dir));
        try {
            if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                Files.move(dir, newdir);
            } else if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                Files.copy(dir, newdir);
            }
        } catch (FileAlreadyExistsException x) {
            // ignore
        } catch (IOException x) {
            logger.error("Unable to create: %s: %s%n", newdir, x);
            return SKIP_SUBTREE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        try {
            if ((pasteFileOptionSelection == PasteFileOptionSelection.DEFAULT
                    || pasteFileOptionSelection == PasteFileOptionSelection.REPLACE
                    || pasteFileOptionSelection == PasteFileOptionSelection.REINDEX)
                    && Files.exists(target)) {
                Optional<PasteFileOptionSelection> result = pasteFileHandlerOptionDialog.construct().showAndWait();
                result.ifPresent((option) -> pasteFileOptionSelection = option);
                if (!result.isPresent()) {
                    pasteFileOptionSelection = PasteFileOptionSelection.DEFAULT;
                    return TERMINATE;
                }
            }
            switch (pasteFileOptionSelection) {
                case REPLACE:
                case REPLACE_ALL:
                    if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                        Files.move(source, target, copyOptions);
                    } else if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                        Files.copy(source, target, copyOptions);
                    }
                    break;
                case REINDEX:
                case REINDEX_ALL:
                    if (clipboardAction.equals(JMEPlayClipboardFormat.CUT)) {
                        Files.move(source, PathResolver.reindexName(target));
                    } else if (clipboardAction.equals(JMEPlayClipboardFormat.COPY)) {
                        Files.copy(source, PathResolver.reindexName(target));
                    }
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
