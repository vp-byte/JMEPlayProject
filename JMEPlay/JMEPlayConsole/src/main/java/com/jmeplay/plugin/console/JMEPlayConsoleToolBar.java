package com.jmeplay.plugin.console;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.ImageLoader;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Implementation of tool bar for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleToolBar extends VBox {

	private int toolsSpacing;
	private int toolsIconSize;
	private Button buttonClose;
	private ToggleButton toggleButtonExceptions;
	private Button buttonCopy;
	private Button buttonSelectAll;
	private Button buttonClear;

	@Autowired
	private JMEPlayConsoleSettings jmePlayConsoleSettings;
	@Autowired
	private JMEPlayConsoleLocalization jmePlayConsoleLocalization;
	@Autowired
	private JMEPlayConsoleArea jmePlayConsoleArea;
	@Autowired
	private JMEPlayConsoleComponent jmePlayConsoleComponent;

	/**
	 * Initialize JMEPlayConsole
	 */
	@PostConstruct
	private void init() {
		initSettings();
		setSpacing(toolsSpacing);
		initButtonClose();
		initTogleButtonLogExceptions();
		initButtonCopy();
		initButtonClear();
		initButtonSelectAll();

		jmePlayConsoleArea.setOnMouseReleased(event -> updateButtons());
	}

	/**
	 * Initialize settings for console tool bar
	 */
	private void initSettings() {
		toolsSpacing = jmePlayConsoleSettings.iconSpacing();
		toolsIconSize = jmePlayConsoleSettings.iconSize();
	}

	/**
	 * Initialize log exceptions check Box
	 */
	private void initTogleButtonLogExceptions() {
		ImageView exceptionImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_EXCEPTION, toolsIconSize, toolsIconSize);
		toggleButtonExceptions = new ToggleButton(null, exceptionImage);
		String exceptionTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_EXCEPTION_TOOLTIP);
		toggleButtonExceptions.setTooltip(new Tooltip(exceptionTooltipLabel));
		toggleButtonExceptions.setSelected(jmePlayConsoleSettings.writeExceptions());
		toggleButtonExceptions.setOnAction(event -> {
			jmePlayConsoleSettings.setValue(JMEPlayConsoleResources.CONSOLE_WRITE_EXCEPTIONS, toggleButtonExceptions.isSelected());
		});
		getChildren().add(toggleButtonExceptions);
	}

	/**
	 * Initialize close button
	 */
	private void initButtonClose() {
		ImageView closeImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_CLOSE, toolsIconSize, toolsIconSize);
		buttonClose = new Button(null, closeImage);
		String closeTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CLOSE_TOOLTIP);
		buttonClose.setTooltip(new Tooltip(closeTooltipLabel));
		buttonClose.setOnAction(event -> {
			MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
			jmePlayConsoleComponent.label().fireEvent(mouseEvent);
		});
		getChildren().add(buttonClose);
	}

	/**
	 * Initialize copy button
	 */
	private void initButtonCopy() {
		ImageView copyImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_COPY, toolsIconSize, toolsIconSize);
		buttonCopy = new Button(null, copyImage);
		String copyTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_COPY_TOOLTIP);
		buttonCopy.setTooltip(new Tooltip(copyTooltipLabel));
		buttonCopy.setDisable(true);
		buttonCopy.setOnAction(event -> {
			jmePlayConsoleArea.copy();
			updateButtons();
		});
		getChildren().add(buttonCopy);
	}

	/**
	 * Initialize select all button
	 */
	private void initButtonSelectAll() {
		ImageView selectAllImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_SELECTALL, toolsIconSize, toolsIconSize);
		buttonSelectAll = new Button(null, selectAllImage);
		String selectAllTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_SELECTALL_TOOLTIP);
		buttonSelectAll.setTooltip(new Tooltip(selectAllTooltipLabel));
		buttonSelectAll.setDisable(true);
		buttonSelectAll.setOnAction(event -> {
			jmePlayConsoleArea.selectAll();
			updateButtons();
		});
		getChildren().add(buttonSelectAll);
		updateButtons();
	}

	/**
	 * Initialize clear button
	 */
	private void initButtonClear() {
		ImageView clearAllImage = ImageLoader.imageView(this.getClass(), JMEPlayConsoleResources.ICONS_CONSOLE_DELETE, toolsIconSize, toolsIconSize);
		buttonClear = new Button(null, clearAllImage);
		String clearAllTooltipLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CLEARALL_TOOLTIP);
		buttonClear.setTooltip(new Tooltip(clearAllTooltipLabel));
		buttonClear.setDisable(true);
		buttonClear.setOnAction(event -> {
			jmePlayConsoleComponent.clear();
			jmePlayConsoleArea.clear();
			updateButtons();
		});
		getChildren().add(buttonClear);
	}

	/**
	 * Update buttons view
	 */
	void updateButtons() {
		boolean containsText = jmePlayConsoleArea.getText() != null && !jmePlayConsoleArea.getText().isEmpty();
		buttonSelectAll.setDisable(!containsText);
		buttonClear.setDisable(!containsText);

		boolean textSelected = jmePlayConsoleArea.getSelectedText() != null && !jmePlayConsoleArea.getSelectedText().isEmpty();
		buttonCopy.setDisable(!textSelected);
	}
}
