import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Adam's room
 */
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
