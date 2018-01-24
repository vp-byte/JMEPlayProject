package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.image.ImageView;

/**
 * Implementation for area to view formated and styled console output
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleArea extends CodeArea {

	private int toolsIconSize;
	private ContextMenu codeAreaMenu;

	private final String ERROR_PATTERN = "\\[(ERROR)\\]\\s";
	private final String WARN_PATTERN = "\\[(WARN)\\]\\s";
	private final String INFO_PATTERN = "\\[(INFO)\\]\\s";
	private final String SUCCESS_PATTERN = "\\[(SUCCESS)\\]\\s";

	private final String[] PATTERNS = { "(?<ERROR>" + ERROR_PATTERN + ")", "(?<WARN>" + WARN_PATTERN + ")", "(?<INFO>" + INFO_PATTERN + ")", "(?<SUCCESS>" + SUCCESS_PATTERN + ")" };

	private final Pattern COMPILED_PATTERN = Pattern.compile(String.join("|", PATTERNS));

	// Injected

	@Autowired
	private JMEPlayConsoleSettings jmePlayConsoleSettings;

	@Autowired
	private JMEPlayConsoleLocalization jmePlayConsoleLocalization;

	@Autowired
	private JMEPlayConsoleToolBar jmePlayConsoleToolBar;

	/**
	 * Initialize JMEPlayConsoleArea
	 */
	@PostConstruct
	private void init() {
		initSettings();
		setEditable(false);
		setOnMouseClicked(this::processClick);
	}

	/**
	 * Initialize settings for console tool bar
	 */
	private void initSettings() {
		toolsIconSize = jmePlayConsoleSettings.iconSize();
	}

	/**
	 * Handle a mouse click.
	 */
	private void processClick(final MouseEvent event) {

		if (codeAreaMenu != null && codeAreaMenu.isShowing()) {
			codeAreaMenu.hide();
		}

		final MouseButton button = event.getButton();
		if (button == MouseButton.SECONDARY) {
			codeAreaMenu = new ContextMenu();

			MenuItem copyMenuItem = createCopyMenuItem();
			MenuItem selectAllMenuItem = createSelectAllMenuItem();
			SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
			MenuItem clearAllMenuItem = createClearAllMenuItem();

			if (getSelectedText() != null && !getSelectedText().isEmpty()) {
				codeAreaMenu.getItems().add(copyMenuItem);
			}

			if (getText() != null && !getText().isEmpty()) {
				codeAreaMenu.getItems().add(selectAllMenuItem);
				codeAreaMenu.getItems().add(separatorMenuItem);
				codeAreaMenu.getItems().add(clearAllMenuItem);
			}
			codeAreaMenu.show(this, event.getScreenX(), event.getScreenY());
		}
	}

	/**
	 * Create copy MenuItem
	 *
	 * @return menu item
	 */
	private MenuItem createCopyMenuItem() {
		ImageView copyImage = ImageLoader.imageView(getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_COPY, toolsIconSize, toolsIconSize);
		String copyLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_COPY);
		MenuItem copyMenuItem = new MenuItem(copyLabel, copyImage);
		copyMenuItem.setOnAction(eventCopy -> copy());
		return copyMenuItem;
	}

	/**
	 * Create select all MenuItem
	 *
	 * @return menu item
	 */
	private MenuItem createSelectAllMenuItem() {
		ImageView selectAllImage = ImageLoader.imageView(getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_SELECTALL, toolsIconSize, toolsIconSize);
		String selectAllLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_SELECTALL);
		MenuItem selectAllMenuItem = new MenuItem(selectAllLabel, selectAllImage);
		selectAllMenuItem.setOnAction(eventSelectAll -> {
			this.selectAll();
			jmePlayConsoleToolBar.updateButtons();
		});
		return selectAllMenuItem;
	}

	/**
	 * Create clear all MenuItem
	 *
	 * @return menu item
	 */
	private MenuItem createClearAllMenuItem() {
		ImageView clearAllImage = ImageLoader.imageView(getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_DELETE, toolsIconSize, toolsIconSize);
		String clearAllLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CLEARALL);
		MenuItem clearAllMenuItem = new MenuItem(clearAllLabel, clearAllImage);
		clearAllMenuItem.setOnAction(eventClearAll -> {
			clear();
			jmePlayConsoleToolBar.updateButtons();
		});
		return clearAllMenuItem;
	}

	/**
	 * Compute highlighting for code area
	 *
	 * @param text to highlight
	 * @return styles for defined spans
	 */
	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = COMPILED_PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("ERROR") != null ? "error"
					: matcher.group("WARN") != null ? "warn" : matcher.group("INFO") != null ? "info" : matcher.group("SUCCESS") != null ? "success" : null;
			/* never happens */
			assert styleClass != null;
			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

	/**
	 * Write text to code area
	 */
	void writeText(String message) {
		this.replaceText(message);
		this.setStyleSpans(0, computeHighlighting(this.getText()));
	}
}
