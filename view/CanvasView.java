package view;

import model.DrawingModel;
import model.IView;
import model.Shape;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.event.*;
import javax.vecmath.*;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;


public class CanvasView extends JPanel implements IView {
	private DrawingModel model;
	private Canvas c = new Canvas();
	//private JScrollPane sp = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
	//ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private boolean select = false;
	private boolean draged = false;
	public CanvasView(DrawingModel model) {
		super();
		this.model=model;
		this.layoutView();
		this.registerControllers();
		this.model.addView(this);

	}

	public void updateView() {
		this.setPreferredSize(new Dimension(model.getlargestx()+10, model.getlargesty()+10));
		revalidate();
		repaint();
	}
	
	private void layoutView() {

	}
	
	public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
		if (model.holder != null) {model.holder.draw(g2);}
		ArrayList <Shape> shapes = this.model.getArray();
		for (Shape shape : shapes) {
			shape.draw(g2);
		}
	}

	private void registerControllers() {
		MouseInputListener m = new MyMouseController();
		addMouseListener(m);
		addMouseMotionListener(m);
	}
	
	
	private class MyMouseController extends MouseInputAdapter {

                        public void mouseClicked(MouseEvent e) {
				ArrayList<Shape> shapes = model.getArray();
				for( int i = 0; i < shapes.size(); i++) {
					shapes.get(i).setSelect(false);
				}
				for (int i = 0; i < shapes.size(); i++) {	
					if (shapes.get(i).within5(e.getX(), e.getY())) {	
						shapes.get(i).setSelect(true);
						select = true;
						model.setRnum(shapes.get(i).getRotate());
						model.setSnum((int)(shapes.get(i).getScale()*100));
						break;
					}
					else {shapes.get(i).setSelect(false); }
				}
				model.checkSelect();				
                        }
		
			public void mousePressed(MouseEvent e) {
				model.checkSelect();
				model.holder = new Shape();
				model.setX(e.getX());
				model.setY(e.getY());
			}
	
                        public void mouseDragged(MouseEvent e) {
				if (model.getSelected()) {
					draged = true;
					model.setDnumx(e.getX());
					model.setDnumy(e.getY());
					model.dragSelected();
				} else {	
                                	model.holder.addPoint(e.getX(), e.getY());
					repaint();
				}
                        }
			public void mouseReleased(MouseEvent e) {
				if (model.holder != null && model.holder.pointsSize() != 0) { 
				int temp = model.getlineNum();
				model.setlineNum(temp+1);
				model.addShape(model.holder);
				model.holder = null;
				}
				if (draged) { 
					  ArrayList<Shape> shapes = model.getArray();
                                	for (int i = 0; i < shapes.size(); i++) {
						shapes.get(i).setSelect(false);
						shapes.get(i).setX(0);
						shapes.get(i).setY(0);
						shapes.get(i).setDragx(0);
						shapes.get(i).setDragy(0);
					}
					draged = false;
				}
				model.checkSelect();
			}
        }	
}


