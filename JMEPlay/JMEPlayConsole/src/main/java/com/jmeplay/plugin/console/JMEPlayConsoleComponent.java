package com.jmeplay.plugin.console;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.PostConstruct;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.editor.ui.JMEPlayComponent;
import com.jmeplay.editor.ui.JMEPlayConsole;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * Implementation for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleComponent extends JMEPlayComponent implements JMEPlayConsole {

	private int iconSizeBar;
	private boolean writeException;
	private StringBuilder stringBuilder = new StringBuilder();
	private Label label;
	private StackPane stackPane;
	private BorderPane borderPane;

	@Autowired
	private JMEPlayConsoleSettings jmePlayConsoleSettings;
	
	@Autowired
	private JMEPlayConsoleLocalization jmePlayConsoleLocalization;
	
	@Autowired
	private JMEPlayConsoleArea jmePlayConsoleArea;
	
	@Autowired
	private JMEPlayConsoleToolBar jmePlayConsoleToolBar;

	/**
	 * Initialize JMEPlayConsole
	 */
	@PostConstruct
	private void init() {
		this.setPriority(0);
		setAlignment(Align.BOTTOM);
		initSettings();
		String consoleLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CONSOLE);
		label = new Label(consoleLabel, ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_CONSOLE, iconSizeBar, iconSizeBar));

		initStackPane();
		borderPane = new BorderPane();
		borderPane.setMinHeight(0);
		borderPane.setLeft(jmePlayConsoleToolBar);
		borderPane.setCenter(stackPane);
	}

	/**
	 * Initialize stack pane
	 */
	private void initStackPane() {
		stackPane = new StackPane(new VirtualizedScrollPane<>(jmePlayConsoleArea));
		stackPane.getStylesheets().add(getClass().getResource(JMEPlayConsoleResources.CSS).toExternalForm());
	}

	/**
	 * Initialize settings for console tool bar
	 */
	private void initSettings() {
		writeException = jmePlayConsoleSettings.writeExceptions();
		iconSizeBar = jmePlayConsoleSettings.iconSizeBar();
	}

	/**
	 * {@link EditorComponent:name}
	 *
	 * @return name
	 */
	@Override
	public String name() {
		return jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CONSOLE);
	}

	/**
	 * {@link EditorComponent:description}
	 *
	 * @return description
	 */
	@Override
	public String description() {
		return jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_DESCRIPTION);
	}

	/**
	 * {@link EditorComponent:label}
	 *
	 * @return label
	 */
	@Override
	public Label label() {
		return label;
	}

	/**
	 * {@link EditorComponent:component}
	 *
	 * @return component as node
	 */
	@Override
	public Node component() {
		return borderPane;
	}

	/**
	 * {@link JMEPlayConsole:message}
	 */
	public void message(Type type, String message) {
		String text = "\n[" + type.name() + "] : " + message;
		stringBuilder.insert(0, text);
		jmePlayConsoleArea.writeText(stringBuilder.toString());
		jmePlayConsoleToolBar.updateButtons();
	}

	/**
	 * {@link JMEPlayConsole:writeException}
	 */
	public void exception(Exception exception) {
		if (writeException) {
			message(Type.ERROR, stackTraceToString(exception));
		}
	}

	/**
	 * Clear text buffer
	 */
	void clear() {
		stringBuilder = new StringBuilder();
	}

	/**
	 * Convert exception stack trace to string
	 *
	 * @param exception to get stack trace
	 * @return stack trace as string
	 */
	private String stackTraceToString(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}

}
