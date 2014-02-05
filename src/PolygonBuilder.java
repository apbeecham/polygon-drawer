/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.io.*;
/**
 *
 * @author Adam's room
 */
public class PolygonBuilder implements Runnable {

    public PolygonBuilder(SketchPanel aPanel,int aMergeDistance){
        panel = aPanel;
        manager = panel.getPolygonManager();
        listener = panel.getSketchListener();
        mergeDistance = aMergeDistance;
    }

    public void run(){
        try{
            manager.buildPolygon(listener.getPoints(),mergeDistance);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,
            "Please sketch a shape first.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }
        finally{
            panel.repaint();
        }

    }
    private PolygonManager manager;
    private SketchListener listener;
    private SketchPanel panel;
    private int mergeDistance;

}

