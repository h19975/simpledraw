package view;

import model.DrawingModel;
import model.IView;
import model.Shape;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.text.NumberFormat;
import javax.swing.BoxLayout;

public class ToolbarView extends JPanel implements IView {
	private DrawingModel model;
	private JButton delete = new JButton("Delete");
	private JButton clear = new JButton("Clear");
	private JLabel sname = new JLabel("Scale");
	private JSlider scale = new JSlider(50, 200);
	private JLabel snum = new JLabel("1.0");
	private JLabel rname = new JLabel("Rotate");
	private JSlider rotate = new JSlider(-180, 180);
	private JLabel rnum = new JLabel("0");
	public ToolbarView(DrawingModel model) {
		super();
		this.model = model;
		this.layoutView();
		this.registerControllers();
		this.model.addView(this);
	}

	private void layoutView() {
		this.add(Box.createHorizontalStrut(20));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(this.clear);
		this.add(Box.createHorizontalStrut(30));
		this.add(this.delete);
		this.add(Box.createHorizontalStrut(30));
		this.add(this.sname);
		this.add(Box.createHorizontalStrut(30));
		this.add(this.scale);
		this.add(Box.createHorizontalStrut(30));
                this.add(this.snum);
		this.add(Box.createHorizontalStrut(30));
		this.add(this.rname);
		this.add(Box.createHorizontalStrut(30));
                this.add(this.rotate);
		this.add(Box.createHorizontalStrut(30));
		this.add(this.rnum);
		this.add(Box.createHorizontalStrut(30));
		this.scale.setValue(100);
		this.rotate.setValue(0);
	}

	public void updateView() {
		scale.setValue((int) model.getSnum());
		snum.setText(Double.toString(model.getSnum()/100.0));
		rotate.setValue((int) model.getRnum());
		rnum.setText(Integer.toString(model.getRnum()));
		if (model.getSelected() == false) {
			delete.setEnabled(false);
			scale.setEnabled(false);
			rotate.setEnabled(false);
		} else {
			delete.setEnabled(true);
                        scale.setEnabled(true);
                        rotate.setEnabled(true);
		}
		
	}

	private void registerControllers() {
		this.scale.addChangeListener(new scaleController());
		this.clear.addActionListener(new clearController());
		this.rotate.addChangeListener(new rotateController());
		this.delete.addActionListener(new deleteController());
	}

	private class deleteController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.deleteSelected();
		}
	}	
		
	private class scaleController implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int s = scale.getValue();
			model.setSnum(s);
			model.scaleSelected();
		}
	}

	private class clearController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.clearCanvas();
			model.setlineNum(0);
		}
	}

	private class rotateController implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int r = rotate.getValue();
			model.setRnum(r);
			model.rotateSelected();
		}
	}
}

		
