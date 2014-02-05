import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author Adam's room
 */
public class PolygonManager{

    public PolygonManager(){
        completePolygons = new ArrayList();
        
    }

    public void buildPolygon(Point[] points,int mergeDistance) throws IOException{
        if(points.length < 2 )
            throw new IOException();

        Polygon polygon = new Polygon();
        boolean[] pointAdded =  new boolean[points.length];
        boolean done = false;
        boolean first = true;
        boolean allowBuild = true;
        int i = 0;

        Point currentPoint = points[0];
        polygon.addPoint(currentPoint.x,currentPoint.y);
        pointAdded[0] = true;
        i = findClosestPoint(currentPoint,points,pointAdded,mergeDistance);

        while(!done){
            if(i < 0){
                allowBuild = false;
                break;
            }
            if(!first && currentPoint.equals(points[0]))
                done = true;

            if(done)
                break;

            currentPoint = points[i];
            polygon.addPoint(currentPoint.x,currentPoint.y);
            pointAdded[i] = true;
            i = findClosestPoint(currentPoint,points,pointAdded,mergeDistance);
            first = false;
        }
        if(allowBuild)
            completePolygons.add(polygon);
    }

    private int findClosestPoint(Point currentPoint, Point[] points, boolean[] pointAdded,int mergeDistance){
        double shortestDistance = 100000;
        double thisDistance;
        int index = 0;

        for(int i = 0; i < points.length; i++){
            thisDistance = calcDistance(currentPoint,points[i]);

            if(thisDistance < shortestDistance && !pointAdded[i]){
                    shortestDistance = thisDistance;
                    index = i;
            }
        }

        try{
            mergePoints(points[index],points,pointAdded,mergeDistance);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,
            "Shape is not connected!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
            index = -1;
        }
        return index;
        
    }

    private void mergePoints(Point currentPoint, Point points[], boolean[] pointAdded,int mergeDistance)throws IOException{
        boolean shapeIsConnected = false;
        for(int i = 0; i < points.length; i++){
            double distance = calcDistance(currentPoint,points[i]);
        
            if(distance <= mergeDistance){
                pointAdded[i] = true;
                if(currentPoint != points[i])
                   shapeIsConnected = true;
            }
        }
        if(!shapeIsConnected)
            throw new IOException();

    }

    private double calcDistance(Point p1, Point p2){
        double distance = Math.sqrt(Math.pow(p1.x - p2.x,2)
                                  + Math.pow(p1.y - p2.y,2)); 
        return distance;
    }

    public void drawPolygons(Graphics g){
        for(Polygon p : completePolygons){
            g.setColor(Color.BLACK);
            g.drawPolygon(p);
            g.setColor(Color.BLUE);
            g.fillPolygon(p);
        }
    }

    public void clearAll(){
        completePolygons = new ArrayList<Polygon>();
    }

    private ArrayList<Polygon> completePolygons;


    
}

