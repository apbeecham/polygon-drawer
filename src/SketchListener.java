import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Adam's room
 */
public class SketchListener extends MouseAdapter implements MouseMotionListener
{

    public SketchListener(){
        endPoints = new ArrayList<Point>();
    }


    public void mousePressed(MouseEvent e) {
        firstPoint = e.getPoint();
        lastPoint = e.getPoint();
    }

    public void mouseDragged(MouseEvent e) {
        currentPoint = e.getPoint();
        Graphics g = ((JPanel)e.getSource()).getGraphics();
    
        g.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
        lastPoint = currentPoint;
        g.dispose();

    }
  
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
