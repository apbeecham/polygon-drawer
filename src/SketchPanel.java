import javax.swing.*;
import java.awt.*;

//provides a space for the user to sketch on
//and draws any polygons built by the polygon manager
public class SketchPanel extends JPanel{

    public SketchPanel(){
        setBackground(Color.WHITE);

        listener = new SketchListener();
        manager = new PolygonManager();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        manager.drawPolygons(g);

    }

    public SketchListener getSketchListener(){
        return listener;
    }

    public PolygonManager getPolygonManager(){
        return manager;
    }
    private final SketchListener listener;
    private final PolygonManager manager;
}
