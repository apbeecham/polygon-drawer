import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

//converts user sketches into straight edged polygons and
//keeps track of all polygons on the screen
public class PolygonManager{

    public PolygonManager(){
        completePolygons = new ArrayList();

    }

	//builds polygons out of user sketches
    public void buildPolygon(Point[] points,int mergeDistance) throws IOException{
        //can't draw anything out of a single point!
        if(points.length < 2 )
            throw new IOException();

        Polygon polygon = new Polygon();
        boolean[] pointAdded =  new boolean[points.length];
        boolean done = false;
        boolean first = true;
        boolean allowBuild = true;
        int i = 0;


        Point currentPoint = points[0];
        //add the current point to the shape
        polygon.addPoint(currentPoint.x,currentPoint.y);
        pointAdded[0] = true;
        //find the next closest point
        i = findClosestPoint(currentPoint,points,pointAdded,mergeDistance);

		//connect points until all points in the shape are connected
        while(!done){
            if(i < 0){
                allowBuild = false;
                break;
            }
            //we've connected all the points
            if(!first && currentPoint.equals(points[0]))
                done = true;

            if(done)
                break;

			//add the current point to the shape and find the next closest point
            currentPoint = points[i];
            polygon.addPoint(currentPoint.x,currentPoint.y);
            pointAdded[i] = true;
            i = findClosestPoint(currentPoint,points,pointAdded,mergeDistance);
            first = false;
        }
        //draw the polygon if there are no problems
        if(allowBuild)
            completePolygons.add(polygon);
    }

	//find the nearest point to another point
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

		//merge two closest points together
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

	//merge a point with its nearest neighbour if it falls within the merge distance
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
        //if the closest point is too far away the shape cannot be completed and
        //should not be drawn
        if(!shapeIsConnected)
            throw new IOException();

    }

	//get the distance between two points
    private double calcDistance(Point p1, Point p2){
        double distance = Math.sqrt(Math.pow(p1.x - p2.x,2)
                                  + Math.pow(p1.y - p2.y,2));
        return distance;
    }

	//draw all polygons
    public void drawPolygons(Graphics g){
        for(Polygon p : completePolygons){
            g.setColor(Color.BLACK);
            g.drawPolygon(p);
            g.setColor(Color.BLUE);
            g.fillPolygon(p);
        }
    }

	//clear all drawn polygons
    public void clearAll(){
        completePolygons = new ArrayList<Polygon>();
    }

    private ArrayList<Polygon> completePolygons;



}

