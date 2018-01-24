package com.jmeplay.plugin.assets;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.editor.ui.JMEPlayComponent;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

@Component
public class JMEPlayAssetsComponent extends JMEPlayComponent {
	private final String name = "assets";
	private final String description = "Component to manage properties of specific selected item";
	private Label label;
	private BorderPane component;

	@Autowired
	private JMEPlayAssetsTreeView jmePlayAssetsComponent;

	@PostConstruct
	private void init() {
		this.setPriority(0);
		setAlignment(Align.LEFT);
		label = new Label("Assets");
		component = new BorderPane(jmePlayAssetsComponent);
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