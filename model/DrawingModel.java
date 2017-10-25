package model;

import java.util.ArrayList;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.lang.*;
import javax.swing.*;
import javax.vecmath.*;
public class DrawingModel extends Object {
	private ArrayList<IView> views = new ArrayList<IView>();
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
		
       private  int snum=100;
	private int rnum=0;
	private int lineNum=0;
	private int dnumx = 0;
	private int dnumy = 0;
	private double X= 0;
	private double Y=0;
	public Shape holder = null;
	private boolean selected = false;
	private String state = new String("");

	public DrawingModel() { }
	
	public void setX(double x) {
		X=x;
	}

	public void setY(double y) {
		Y=y;
	}
	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	public ArrayList<Shape> getArray() {
		return shapes;
	}

	public void setDnumx(int d) {
		this.dnumx = d;
	}
	
	public void setDnumy(int d) {
		this.dnumy = d;
	}

	public int getDnumx() {
		return this.dnumx;
	}

	public int getDnumy() {
		return this.dnumy;
	}

	public void setSnum(int snum) {
		this.snum=snum;
		this.updateAllViews();
	}

	public void setRnum(int rnum) {
		this.rnum=rnum;
		this.updateAllViews();
	}

	public void setlineNum(int ln) {
		this.lineNum=ln;
		this.updateAllViews();
	}

	public int getSnum() {
		return this.snum;
	}

	public int getRnum() {
		return this.rnum;
	}

	public int getlineNum() {
		return this.lineNum;
	}
	
	public void addView(IView view) {
		this.views.add(view);
		view.updateView();
	}

	public void addShape(Shape shape) {
		this.shapes.add(shape);
	}

	public void removeView(IView view) {
		this.views.remove(view);
	}

	private void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
	}
	
	public void clearCanvas() {
		for (Shape shape: this.shapes) {
			shape.clearPoints();
			shape.setSelect(false);
		}
		checkSelect();
		this.shapes.clear();
	}

	
	public void deleteSelected() {
		int i = 0;
		for (; i < this.shapes.size(); i++) {
			if (shapes.get(i).getSelect() == true) {
				shapes.get(i).clearPoints();
				break;
			}
		}
		this.shapes.remove(i);
		lineNum --;
		checkSelect();		
	}
	
	public void scaleSelected() {
		float scale = (float) (snum/100.0);
		for (Shape shape: this.shapes) {
			if (shape.getSelect() == true) {
				shape.setScale(scale);
				break;
			}
		}
		checkSelect();
	}
		
	public void rotateSelected() {
		for (Shape shape : this.shapes) {
			if (shape.getSelect() == true) {
				shape.setRotate(rnum);
				break;
			}
		}
		checkSelect();
	}

	public void dragSelected() {
		for (Shape shape : this.shapes) {
			if (shape.getSelect() == true) {
				shape.setDragx(dnumx);
				shape.setDragy(dnumy);
				shape.setX(X);
				shape.setY(Y);
				break;
			}
		}
		checkSelect();
		X = dnumx;
		Y = dnumy;
	}
	
	public void checkSelect() {
		selected = false;
		for (Shape shape : this.shapes) {
			if (shape.getSelect() == true) {
				selected =  true;
				state = ", Selection(" + Integer.toString(shape.npoints()) +
					" points, scale: " + Float.toString(shape.getScale()) +
					", rotation: "+Integer.toString(shape.getRotate())+
					")";
				break;
			}
		}
		if (!selected) {state = ""; rnum = 0; snum = 100;};
		updateAllViews();
	}
	
	public String getState() {
		return state;
	}
	
	public boolean getSelected() {
		return selected;
	} 
		
	public int getlargestx() {
		int max = 0;
		for (Shape shape : this.shapes) {
			if (shape.getXl() > max) {
				max = shape.getXl();
			}
		}
		return max;
	}

	public int getlargesty() {
		int max = 0;
		for (Shape shape : this.shapes) {
			if (shape.getYl() > max) {
				max = shape.getYl();
			}
		}
		return max;
	}
				
}
			
		
