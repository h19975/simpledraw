package model;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.*;
import java.awt.event.*;
import javax.vecmath.*;
import java.lang.*;
import java.lang.Math.*;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.geom.*;
// simple shape model class
public class Shape { 
    // shape points
    ArrayList<Point2D> points;
	int xl;
	int yl;

	public int getXl() {
		return this.xl;
	}

	public int getYl() {
		return this.yl;
	}


public int pointsSize() {
	if (points == null) return 0;
	else{
	return points.size();
	}
	}
	
 	boolean select = false;
	public void setSelect(boolean bool) {
		select = bool;
	}
	public boolean getSelect() {
		return select;
	}
	
    public void clearPoints() {
        points = new ArrayList<Point2D>();
        pointsChanged = true;
    }
  
    // add a point to end of shape
    public void addPoint(Point2D p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }    
    // add a point to end of shape
    public void addPoint(double x, double y) {
        addPoint(new Point2D.Double(x, y));  
    }
    public int npoints() {
        return points.size();
    }
    // shape is polyline or polygon
    Boolean isClosed = false; 
    public Boolean getIsClosed() {
        return isClosed;
    }
    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }    
    // if polygon is filled or not
    Boolean isFilled = false; 
    public Boolean getIsFilled() {
        return isFilled;
    }
    public void setIsFilled(Boolean isFilled) {
        this.isFilled = isFilled;
    }    
    // drawing attributes
    Color colour = Color.BLACK;
    float strokeThickness = 2;
    public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
    public float getStrokeThickness() {
		return strokeThickness;
	}
	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}
    // shape's transform
    // quick hack, get and set would be better
    float scale = 1.0f;
	int rotate = 0;
	double dragx = 0;
	double dragy = 0;
	double X=0;
	double Y=0;
	public int centerx = 0; 
	public int centery = 0;
    // some optimization to cache points for drawing
    Boolean pointsChanged = false; // dirty bit
	
	public void setX(double x) {
		X=x;
	}

	public void setY(double y) {
		Y=y;
	}
	
	public void setDragy(double d) {
		dragy = d;
	}

	public void setDragx(double d) {
		dragx = d;
	}
	
	
	public void setScale(float s) {
		scale = s;
	}

	public float getScale() {
		return scale;
	}
	
	public void setRotate(int r) {
		rotate = r;
	}

	public int getRotate() {
		return rotate;
	}

    int[] xpoints, ypoints;
    int npoints = 0;
    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
            xpoints[i] = (int)points.get(i).getX();
            ypoints[i] = (int)points.get(i).getY();
        }
        npoints = points.size();
        pointsChanged = false;
    }
	 public int center(int[] a,int size) {
                int min = a[0];
		int max = a[0];
                for (int i = 0; i < size; i++) {
                        if (a[i] < min) { min = a[i];};
			if (a[i] > max) {max = a[i];};
                }
                return (int)((min+max)/2);
        }

	private Point2d newpoint(double x, double y, int centerx, int centery, float angle, float scale) {
		Point2D ret = new Point2D.Double(x, y);
		//AffineTransform.getTranslateInstance(-center(xpoints, npoints)*(scale-1), -center(ypoints, npoints)*(scale-1)).transform(ret,ret);
		AffineTransform.getRotateInstance(Math.toRadians(angle), centerx, centery).transform(ret,ret);
		AffineTransform.getTranslateInstance(-center(xpoints, npoints)*(scale-1), -center(ypoints, npoints)*(scale-1)).transform(ret,ret);
		AffineTransform.getScaleInstance(scale, scale).transform(ret,ret);
		Point2d p = new Point2d(ret.getX(), ret.getY());
		return p;
	}
		
	private void largest() {
		int ly = 0;
		int lx = 0;
		for (int i =0; i < points.size(); i++) {
			Point2d newi = newpoint(points.get(i).getX(), points.get(i).getY(), center(xpoints, npoints),
					center(ypoints, npoints), rotate, scale);
			if (newi.getX() > lx) {lx = (int)newi.getX();}
			if (newi.getY() > ly) {ly = (int)newi.getY();}
		}
		yl = ly;
		xl = lx;
		
	}	




	static Point2d closestPoint(Point2d p1, Point2d p2, Point2d p3) {
		Vector2d v = new Vector2d();
		v.sub(p3, p2);
		if (v.lengthSquared() < 0.5) {
			return p2;
		}
		Vector2d u = new Vector2d();
		u.sub(p1, p2);

		double s = u.dot(v) /v.dot(v);
		if (s < 0) {return p2;}
		else if (s > 1) {return p3;}
		else {
			Point2d i = p2;
			Vector2d w = new Vector2d();
			w.scale(s,v);
			i.add(w);
			return i;
		}
	}

	
 	public boolean within5(double x, double y) {
		cachePointsArray();
  		for (int i = 0; i < points.size()-1; i++) {
			Point2d myp = newpoint(points.get(i).getX(), points.get(i).getY(), center(xpoints,npoints),
				center(ypoints, npoints), rotate, scale);
			Point2d p2 = newpoint(points.get(i+1).getX(), points.get(i+1).getY(), center(xpoints,npoints),
				center(ypoints, npoints), rotate, scale);
			Point2d p5 = new Point2d(x, y);
			Point2d p3 = closestPoint(p5, myp, p2);
			double dis = (p3.getX()-x)*(p3.getX()-x)+
				(p3.getY()-y)*(p3.getY()-y);
			if (dis <= 25.0*scale) {return true;}
		}
		return false;
	}

 
	
	public void drag() {
		for (int i = 0; i < points.size(); i++) {
         	     	Point2D newi =  new Point2D.Double(points.get(i).getX()+dragx-X,
			points.get(i).getY()+dragy-Y);
			points.set(i, newi);
        	}
		pointsChanged = true;
	}
	
    public void draw(Graphics2D g2) {
        // don't draw if points are empty (not shape)
        if (points == null) return;
        // see if we need to update the cache
        // save the current g2 transform matrix 
       // AffineTransform M = g2.getTransform();
        // multiply in this shape's transform
        // (uniform scale)
	if (dragx-X !=0 || dragy-Y != 0) {drag();};
	if (pointsChanged) { cachePointsArray();}
	largest();
	AffineTransform M = g2.getTransform();
	centerx = center(xpoints, npoints);
	centery = center(ypoints, npoints);	
	g2.rotate(Math.toRadians(rotate), centerx, centery);
	g2.translate(-centerx*(scale-1), -centery*(scale-1));
	g2.scale(scale, scale);
        // call drawing functions         
	if (isFilled) {
            g2.fillPolygon(xpoints, ypoints, npoints);
        } else {
            // can adjust stroke size using scale
		if (select) { g2.setStroke(new BasicStroke(6.0f / scale)); g2.setColor(Color.YELLOW);
				g2.drawPolyline(xpoints, ypoints, npoints); }
        	g2.setStroke(new BasicStroke(strokeThickness / scale)); 
		g2.setColor(colour);
        	if (isClosed)
                g2.drawPolygon(xpoints, ypoints, npoints);
            else
                g2.drawPolyline(xpoints, ypoints, npoints);
        }
        // reset the transform to what it was before we drew the shape
       g2.setTransform(M);            
    }
}
