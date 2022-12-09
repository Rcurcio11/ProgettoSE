import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.EllipseModel;
import seproject.LineModel;
import seproject.LoadDrawingFromFileCommand;
import seproject.PolygonModel;
import seproject.RectangleModel;
import seproject.ShapeModel;


/**
 *
 * @author Group14
 */
public class LoadDrawingFromFileCommandTest {
    
    private final LoadDrawingFromFileCommand testLoadDrawingFromFileCommand;
    private final AnchorPane testDrawingArea;
    private final String testFilePath;
    
    
    public LoadDrawingFromFileCommandTest() {
        testFilePath="LoadDrawingOnFileCommandTest.txt";
        testDrawingArea = new AnchorPane();
        testLoadDrawingFromFileCommand=new LoadDrawingFromFileCommand(testDrawingArea, testFilePath);
    }
  
    @Before
    public void setUp() throws FileNotFoundException {
        
       

    }

    @Test
    public void testExecute() throws FileNotFoundException{
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D(10,12));
        points.add(new Point2D(14,16)); 
        Color stroke = Color.BLACK;
        Color fill = Color.RED;
        
        ShapeModel line = new LineModel();
        line.insert(testDrawingArea, points,stroke,fill);
        ShapeModel rect = new RectangleModel();
        rect.insert(testDrawingArea,points,stroke,fill);
        ShapeModel ellipse = new EllipseModel();
        ellipse.insert(testDrawingArea,points,stroke,fill);
        points.add(new Point2D(20,26)); 
        points.add(new Point2D(10,12));
        ShapeModel polygon = new PolygonModel();
        polygon.insert(testDrawingArea,points,stroke,fill);
        
        
        PrintWriter saver = new PrintWriter(new FileOutputStream(testFilePath));
        for(Node n:testDrawingArea.getChildren()){
            ShapeModel shape = (ShapeModel)n;
            saver.print(shape.saveOnFileString(";"));
        }
        saver.close();
        testDrawingArea.getChildren().clear();
        
        testLoadDrawingFromFileCommand.execute();
        assertEquals(testDrawingArea.getChildren().size(),4);
        
        ShapeModel check = null;
        int counter = 0;
        
        for(Node n :testDrawingArea.getChildren()){
            if(n.getClass().equals(RectangleModel.class)){
                check = (ShapeModel)n;
                assertEquals(check.getLowerBound(), rect.getLowerBound());
                counter++;
            }
            if(n.getClass().equals(EllipseModel.class)){
                check = (ShapeModel)n;
                assertEquals(check.getLowerBound(), ellipse.getLowerBound());
                counter++;
            }
            if(n.getClass().equals(LineModel.class)){
                check = (ShapeModel)n;
                assertEquals(check.getLowerBound(), line.getLowerBound());
                counter++;
            }
            if(n.getClass().equals(PolygonModel.class)){
                check = (ShapeModel)n;
                assertEquals(check.getLowerBound(), polygon.getLowerBound());
                counter++;
            }
        }
        assertEquals(counter,4);
        

    }
}