

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.EllipseModel;
import seproject.FileErrorDrawException;
import seproject.LineModel;
import seproject.LoadDrawingFromFileCommand;
import seproject.RectangleModel;
import seproject.SaveDrawingOnFileCommand;
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
        
        Point2D startPoint = new Point2D(10,12);
        Point2D endPoint = new Point2D(14,16);
        Color stroke = Color.BLACK;
        Color fill = Color.RED;
        ShapeModel line = new LineModel();
        line.insert(testDrawingArea, startPoint, endPoint,stroke,fill);
        ShapeModel rect = new RectangleModel();
        rect.insert(testDrawingArea,startPoint,endPoint,stroke,fill);
        ShapeModel ellipse = new EllipseModel();
        ellipse.insert(testDrawingArea,startPoint,endPoint,stroke,fill);
        line = new LineModel();
        line.insert(testDrawingArea, startPoint, endPoint,stroke,fill);
        
        PrintWriter saver = new PrintWriter(new FileOutputStream(testFilePath));
        for(Node n:testDrawingArea.getChildren()){
            ShapeModel shape = (ShapeModel)n;
            saver.print(shape.saveOnFileString(";"));
        }
        saver.close();

    }

    @Test
    public void testExecute() throws FileNotFoundException{
        
                
        testLoadDrawingFromFileCommand.execute();
        
        Scanner loader = new Scanner(new FileInputStream(testFilePath)).useDelimiter(";").useLocale(Locale.US);
        
        String className = loader.next();
        ShapeModel shape = new LineModel();
        double startX = loader.nextDouble();
        double startY = loader.nextDouble();
        double endX = loader.nextDouble();
        double endY = loader.nextDouble();
        String stroke = loader.next();
        String fill = loader.next();

        Point2D startPoint = new Point2D(startX,startY);
        Point2D endPoint = new Point2D(endX,endY);
        
        assertEquals(className,LineModel.class.getSimpleName());
        assertEquals(startPoint,new Point2D(10,12));
        assertEquals(endPoint,new Point2D(14,16));
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.BLACK.toString());
        
        className = loader.next();
        shape = new RectangleModel();
        startX = loader.nextDouble();
        startY = loader.nextDouble();
        endX = loader.nextDouble();
        endY = loader.nextDouble();
        stroke = loader.next();
        fill = loader.next();
        startPoint = new Point2D(startX,startY);
        endPoint = new Point2D(endX,endY);
        assertEquals(className,RectangleModel.class.getSimpleName());
        assertEquals(startPoint,new Point2D(10,12));
        assertEquals(endPoint,new Point2D(14,16));
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.RED.toString());
        
        className = loader.next();
        shape = new EllipseModel();
        startX = loader.nextDouble();
        startY = loader.nextDouble();
        endX = loader.nextDouble();
        endY = loader.nextDouble();
        stroke = loader.next();
        fill = loader.next();
        startPoint = new Point2D(startX,startY);
        endPoint = new Point2D(endX,endY);
        assertEquals(className,EllipseModel.class.getSimpleName());
        assertEquals(startPoint,new Point2D(10,12));
        assertEquals(endPoint,new Point2D(14,16));
        assertEquals(stroke,Color.BLACK.toString());
        assertEquals(fill,Color.RED.toString());

    }
}
