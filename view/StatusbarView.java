package view;

import model.DrawingModel;
import model.IView;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.Box;
import java.awt.BorderLayout;

public class StatusbarView extends JPanel implements IView {
	private DrawingModel model;
	private JLabel state = new JLabel("0 Strokes");
	private String text = "0 Strokes";
	public StatusbarView(DrawingModel model) {
		super();
		this.model=model;
		this.layoutView();
		this.registerControllers();
		this.model.addView(this);
	}
	public void updateView() {
		text = Integer.toString(model.getlineNum()) + " Strokes";
		if (model.getSelected()) { 
			text += model.getState();
		};
		state.setText(text);
	}
	private void layoutView() {
		this.setLayout(new BorderLayout());
		this.add(this.state);
	}
	private void registerControllers() {
		
	}
	
}
