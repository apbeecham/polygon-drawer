import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class PolygonFrame extends JFrame {

    public static void main(String args[]){
        PolygonFrame frame = new PolygonFrame(750,750);
    }

    public PolygonFrame(int width, int height){
        setTitle("Polygon Sketcher");
        setSize(width , height);

        addMenu();
        addSketchPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              sketchPanel, menu);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(600);
        add(splitPane);

        mergeDistance = 25;

        setVisible(true);
    }

    private void addMenu(){
        menu = new JPanel();
        menu.setLayout(new BorderLayout());
        addBasicOptions();
        addMergingOptions();
    }

    private void addBasicOptions(){
        JPanel basicOptions = new JPanel();
        basicOptions.setLayout(new GridLayout(2,1,0,20));
        basicOptions.setBorder(new TitledBorder(new EtchedBorder(),"Canvas Options"));
        JButton drawPolygon = new JButton("Create Polygon");
        JButton clearCanvas = new JButton("Clear Polygons");

        drawPolygon.setToolTipText("Press to convert a sketch into a polygon.");
        clearCanvas.setToolTipText("Press to clear the canvas of all polygons.");
        
        class BuildListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                Runnable builder = new PolygonBuilder(sketchPanel,mergeDistance);
                Thread backgroundThread = new Thread(builder);
                backgroundThread.start();
            }
        }
        class ClearListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                sketchPanel.getPolygonManager().clearAll();
                sketchPanel.repaint();
            }
        }
        drawPolygon.addActionListener(new BuildListener());
        clearCanvas.addActionListener(new ClearListener());

        basicOptions.add(drawPolygon);
        basicOptions.add(clearCanvas);
        menu.add(basicOptions,"West");
    }

    private void addMergingOptions(){
        JPanel mergeOptions = new JPanel();
        mergeOptions.setBorder(new TitledBorder(new EtchedBorder(),"Set Point Merge Radius (pixels)"));
        mergeOptions.setLayout(new GridLayout(1,1));

        mergeSlider = new JSlider(0,100);
        mergeSlider.setMajorTickSpacing(10);
        mergeSlider.setPaintTicks(true);
        mergeSlider.setPaintLabels(true);
        mergeSlider.setToolTipText("Set the merge distance (pixels).Increasing this " +
                                   "value will reduce the number of sides on the polygon.");
        mergeSlider.setValue(25);
        
        class MergeListener implements ChangeListener{
            public void stateChanged(ChangeEvent e){
                setMergeDistance();
            }
        }
        mergeSlider.addChangeListener(new MergeListener());
        mergeOptions.add(mergeSlider);
        
        menu.add(mergeOptions,"Center");
    }

    private void setMergeDistance(){
        mergeDistance = mergeSlider.getValue();
    }

    private void addSketchPanel(){
       sketchPanel = new SketchPanel();
    }

    private SketchPanel sketchPanel;
    private JPanel menu;
    private JSlider mergeSlider;
    private int mergeDistance;
    
}
