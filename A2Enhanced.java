//enhanced functionality: add clear button to clear out all drawings in the current canvas

import javax.swing.JFrame;
import model.DrawingModel;
import view.ToolbarView;
import view.CanvasView;
import view.StatusbarView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.event.*;
import javax.vecmath.*;


public class A2Enhanced {
        public static void main(String[] args) {
                model.DrawingModel model = new DrawingModel();
                ToolbarView tv = new ToolbarView(model);
                CanvasView cv = new CanvasView(model);
                StatusbarView sv = new StatusbarView(model);

                JFrame f = new JFrame("A2Basic");
                f.setSize(800, 600);
                f.getContentPane().setLayout(new BorderLayout());
                f.getContentPane().add(tv, BorderLayout.NORTH);
                JScrollPane sp = new JScrollPane(cv, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                sp.setPreferredSize(new Dimension(800, 500));
                f.getContentPane().add(sp, BorderLayout.CENTER);

                f.getContentPane().add(sv, BorderLayout.SOUTH);
                tv.setSize(800, 50);
                sv.setSize(800, 50);

                f.pack();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
        }
}
