import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class SketchListener extends MouseAdapter implements MouseMotionListener
{

    public SketchListener(){
        endPoints = new ArrayList<Point>();
    }


	//set start and end point of the line in case the
	//user doesn't drag the mouse
    public void mousePressed(MouseEvent e) {
        firstPoint = e.getPoint();
        lastPoint = e.getPoint();
    }

	//enables freehand sketching by drawing very small lines
	//while the user is dragging the mouse
    public void mouseDragged(MouseEvent e) {
        currentPoint = e.getPoint();
        Graphics g = ((JPanel)e.getSource()).getGraphics();

        g.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
        lastPoint = currentPoint;
        g.dispose();

    }

  	//when the user finishes drawing, we only remember the first and last points
  	//and discard the rest so we can join the two with a straight line
    public void mouseReleased(MouseEvent e){
        endPoints.add(firstPoint);
        endPoints.add(lastPoint);
    }

    public Point[] getPoints(){
        Point[] pointArray = new Point[endPoints.size()];
        endPoints.toArray(pointArray);
        endPoints = new ArrayList<Point>();
        return pointArray;
    }


    public void mouseMoved(MouseEvent e) {}

    private Point firstPoint,currentPoint, lastPoint;
    private ArrayList<Point> endPoints;


}
