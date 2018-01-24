package com.jmeplay.editor.plugin;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.jmeplay.editor.ui.JMEPlayComponent;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

@Component
public class PropertyEditorComponent extends JMEPlayComponent {
	private final String name = "Properties";
	private final String description = "Component to manage properties of specific selected item";
	private Label label;
	private StackPane component;

	@PostConstruct
	private void init() {
		this.setPriority(10);
		setAlignment(Align.LEFT);
		label = new Label("Properties");
		component = new StackPane(new Text("Properties"));
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Label label() {
		return label;
	}

	@Override
	public Node component() {
		return component;
	}

}
